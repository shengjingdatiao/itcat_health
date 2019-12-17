package cn.itcast.controller;

import cn.itcast.constant.MessageConstant;
import cn.itcast.entify.Result;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @PackageName: cn.itcast.controller
 * @ClassName: UserController
 * @Author: dongxiyaohui
 * @Date: 2019/12/10 19:14
 * @Description: //TODO
 */
@RestController
@RequestMapping("/user")
public class UserController {
    //获取用户名，用于页面登录展示
    @RequestMapping("/getUsername")
    public Result getUsername(){
        //获取框架的user对象
        User user = null;
        try {
            user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = user.getUsername();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,username);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_USERNAME_FAIL);
        }
    }
    //获取当前用户所拥有的权限
    @RequestMapping("/CheckItem")
    public Map CheckItem(){
        //定义一个map集合，用于存储用户的权限
        Map<String,Boolean> map = new HashMap<>();
        map.put("edit",false);
        map.put("delete",false);
        map.put("new",false);
        //获取springsecurity 的user对象，用于获取当前登录用户的权限
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            //获取当前用户拥有的权限的关键字
            String authority1 = authority.getAuthority();
            if("CHECKITEM_ADD".equals(authority1)){
                map.put("new",true);
            }
            if("CHECKITEM_DELETE".equals(authority1)){
                map.put("delete",true);
            }
            if("CHECKITEM_EDIT".equals(authority1)){
                map.put("edit",true);
            }
        }
         return map;
    }
}
