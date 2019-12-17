package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.constant.RedisMessageConstant;
import cn.itcast.entify.Result;
import cn.itcast.pojo.Member;
import cn.itcast.service.MemberService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @PackageName: cn.itcast.controller
 * @ClassName: MemberController
 * @Author: dongxiyaohui
 * @Date: 2019/12/8 16:32
 * @Description: //TODO
 */
@RestController
@RequestMapping("/member")
public class MemberController {
   @Reference
    private MemberService memberService;
   @Autowired
    JedisPool jedisPool;
   @RequestMapping("/login")
    public Result login(@RequestBody Map map, HttpServletResponse response){
       //获取用户提交的验证码，和保存的验证码作比较
       String telephone = (String) map.get("telephone");
       String validateCode = (String) map.get("validateCode");
       //获取redis中存储的验证码
       String RedisvalidateCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
       //判断用户输入的验证码是否正确
       if(RedisvalidateCode!=null && validateCode !=null && validateCode.equals(RedisvalidateCode)){
           //判断用户是否为会员，如果不是会员则进行自动注册
        Member member =  memberService.findBytelephone(telephone);
        if(member == null){
            //当前用户不是会员，自动注册为会员
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            //注册会员
            memberService.add(member);
        }
          //将用户数据写入cookie中，便于跟踪用户信息
           Cookie cookie = new Cookie("login_member_telephone", telephone);
          //设置路径
           cookie.setPath("/");
           //设置时间
           cookie.setMaxAge(60*60*24*30);
           response.addCookie(cookie);
           //保存会员信息到redis中
           //将java对象转换为json对象
           String memberl = JSON.toJSON(member).toString();
           jedisPool.getResource().setex(telephone,60*30,memberl);
           return new Result(true,MessageConstant.LOGIN_SUCCESS);
       }
           return new Result(false,MessageConstant.VALIDATECODE_ERROR);

   }
}
