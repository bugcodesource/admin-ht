package com.sc.adminht.mapper.system;

import com.sc.adminht.entity.system.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SysRoleMapper {
    //通过用户名查找用户角色信息
    List<SysRole> findRoleByName(String loginName);
}
