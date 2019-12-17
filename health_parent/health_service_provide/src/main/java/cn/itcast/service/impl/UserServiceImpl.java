package cn.itcast.service.impl;

import cn.itcast.mapper.PermissionMapper;
import cn.itcast.mapper.RoleMapper;
import cn.itcast.mapper.UserMapper;
import cn.itcast.pojo.Permission;
import cn.itcast.pojo.Role;
import cn.itcast.pojo.User;
import cn.itcast.service.UserService;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @PackageName: cn.itcast.service.impl
 * @ClassName: UserServiceImpl
 * @Author: dongxiyaohui
 * @Date: 2019/12/10 13:02
 * @Description: //TODO
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleMapper roleMapper;
    @Autowired
    PermissionMapper permissionMapper;

    @Override
    public User findByUsername(String username) {
        //通过用户名称查找用户
        User user = userMapper.findUserByUsername(username);
        if (user != null) {
            Set<Role> roles = roleMapper.findRoleByUsernameId(user.getId());
            if (roles != null && roles.size() > 0) {
                for (Role role : roles) {
                    Set<Permission> permissions = permissionMapper.findpermissionsByRole(role.getId());
                    if (permissions != null && permissions.size() > 0) {
                        role.setPermissions(permissions);
                    }
                }
                user.setRoles(roles);
            }
        }
        return user;
    }
}
