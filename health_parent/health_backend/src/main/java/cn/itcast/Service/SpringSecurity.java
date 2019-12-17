package cn.itcast.Service;

import cn.itcast.pojo.Permission;
import cn.itcast.pojo.Role;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @PackageName: cn.itcast.Service
 * @ClassName: SpringSecurity
 * @Author: dongxiyaohui
 * @Date: 2019/12/10 12:45
 * @Description: //TODO
 */
@Component("springSecurity")
public class SpringSecurity implements UserDetailsService {
    @Reference
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //调用业务层方法通过用户名查询用户数据
        User byUsername = userService.findByUsername(username);
        if(byUsername==null){
            return null;
        }
        //通过用户id查询用户对应的角色
        Set<Role> roles= byUsername.getRoles();
        //
        List<GrantedAuthority> list = new ArrayList<>();
        //遍历到查询的用户角色
        for (Role role : roles) {
            //将查询到的用户角色，设置到集合中
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            //通过角色的id查询对应的每个角色权限
            Set<Permission> permissionByRoleId = role.getPermissions();
            for (Permission permission : permissionByRoleId) {
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        org.springframework.security.core.userdetails.User user =
                new org.springframework.security.core.userdetails.User(username,byUsername.getPassword(),list);
        return user;
    }
}
