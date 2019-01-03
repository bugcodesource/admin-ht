package com.sc.adminht.mapper.system;

import com.sc.adminht.entity.system.SysPermission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysPermissionMapper {
    //根据角色id查询角色对应的权限信息
    List<SysPermission> findPermByRoleId(int roleId);
}
