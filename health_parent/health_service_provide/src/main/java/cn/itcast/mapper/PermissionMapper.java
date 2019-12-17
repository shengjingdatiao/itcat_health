package cn.itcast.mapper;

import cn.itcast.pojo.Permission;

import java.util.Set;

public interface PermissionMapper {
    Set<Permission> findpermissionsByRole(Integer id);
}
