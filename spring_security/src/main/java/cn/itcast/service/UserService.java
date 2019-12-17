package cn.itcast.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @PackageName: cn.itcast.service
 * @ClassName: Uservice
 * @Author: dongxiyaohui
 * @Date: 2019/12/9 20:40
 * @Description: //TODO
 */

public class UserService implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("用户输入的名为"+username);
        return null;
    }
}
