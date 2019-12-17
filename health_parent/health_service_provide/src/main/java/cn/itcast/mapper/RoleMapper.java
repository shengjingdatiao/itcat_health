package cn.itcast.mapper;

import cn.itcast.pojo.Role;

import java.util.Set;

public interface RoleMapper {
    Set<Role> findRoleByUsernameId(Integer id);
}
