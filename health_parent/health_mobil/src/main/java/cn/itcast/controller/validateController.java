package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisMessageConstant;
import cn.itcast.entify.Result;
import cn.itcast.util.SMSUtils;
import cn.itcast.util.ValidateCodeUtils;
import com.aliyuncs.exceptions.ClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * @PackageName: cn.itcast.controller
 * @ClassName: validateController
 * @Author: dongxiyaohui
 * @Date: 2019/12/7 15:57
 * @Description: //TODO
 * 短信验证
 */
@RestController
@RequestMapping("/validate")
public class validateController {
    @Autowired
    JedisPool jedisPool;
    //预约时发送短信验证码
    @RequestMapping("/send4order")
    public Result send4order(String telephone){
        //获取随机的4位数字，作为验证码
        Integer integer = ValidateCodeUtils.generateValidateCode(4);
        //给用户发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,integer.toString());
            //将获取到的验证码，存储到redis数据库中并且设置自动清理时间为5分钟
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,30000,integer.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
    //用户登录时发送验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        //调用工具类设置随机生成的验证码
        String validateCode = ValidateCodeUtils.generateValidateCode4String(6);
        //调用阿里云服务，发送验证码
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,validateCode);
            //将验证码存储到redis中，进行登录时的校验
            jedisPool.getResource().setex(telephone+RedisMessageConstant.SENDTYPE_LOGIN,30000,validateCode);
            return new Result (true,MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (ClientException e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
