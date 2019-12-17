package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisMessageConstant;
import cn.itcast.entify.Result;
import cn.itcast.pojo.Order;
import cn.itcast.service.OrderService;
import cn.itcast.util.SMSUtils;
import cn.itcast.util.ValidateCodeUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * @PackageName: cn.itcast.controller
 * @ClassName: OrderController
 * @Author: dongxiyaohui
 * @Date: 2019/12/7 18:03
 * @Description: //TODO
 */
@RestController
@RequestMapping("/order")
public class OrderController {
   @Autowired
    JedisPool jedisPool;
   @Reference
   private OrderService orderService;
   @RequestMapping("/submit")
    public Result submit(@RequestBody Map map){
       //获取用户输入的验证码
        String validateCode = (String) map.get("validateCode");
      //从redis中获取发送给用户的验证码，先要获取用户填写的手机号
        String telephone = (String) map.get("telephone");
        String sendcode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
       //将用户输入验证码和发送的验证码作一个比较
       Result result =null;
       if( sendcode !=null && validateCode.equals(validateCode)){
           //验证用户输入的验证码和发送的验证码一致,调用业务逻辑层的方法
           map.put("orderType", Order.ORDERTYPE_WEIXIN);
           //调用业务层的方法时，可能存在业务层的方法发生异常，无法被调用
           try {
                result = orderService.submit(map);
               //业务层方法调用成功后，向用户发送短信，进行提示预约日期的提示
               if(result.isFlag()){
                   //获取用户提交的预约的日期
                  String orderDate = (String) map.get("orderDate");
                  //调用阿里云工具类发送短信
                   String s = ValidateCodeUtils.generateValidateCode4String(4);
                   System.out.println(4);
                   SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,s);
               }
               return result;
           } catch (Exception e) {
               e.printStackTrace();
               //预约失败
               return result;
           }
       }else{
              return new Result(false,MessageConstant.VALIDATECODE_ERROR);
       }
   }
   @RequestMapping("/findById")
    public Result findById(Integer id){
       //调用业务层方法，进行查询
       try {
           Map map=orderService.findById(id);
           return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
       } catch (Exception e) {
           e.printStackTrace();
           return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
       }

   }
}
