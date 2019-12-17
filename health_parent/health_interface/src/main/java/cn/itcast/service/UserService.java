package cn.itcast.service;

import cn.itcast.pojo.Permission;
import cn.itcast.pojo.Role;
import cn.itcast.pojo.User;

import java.util.Set;

/**
 * @PackageName: cn.itcast.service
 * @ClassName: UserService
 * @Author: dongxiyaohui
 * @Date: 2019/12/10 12:46
 * @Description: //TODO
 */
public interface UserService {
    User findByUsername(String username);
}
