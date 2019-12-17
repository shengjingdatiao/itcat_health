package cn.itcast.mapper;

import cn.itcast.pojo.User;

/**
 * @PackageName: cn.itcast.mapper
 * @ClassName: UserMapper
 * @Author: dongxiyaohui
 * @Date: 2019/12/10 13:08
 * @Description: //TODO
 */
public interface UserMapper {

    User findUserByUsername(String username);
}
