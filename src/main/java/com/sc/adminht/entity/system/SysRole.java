package com.sc.adminht.entity.system;

import lombok.Data;

@Data
public class SysRole {
    private Integer roleId;
    private String role;                              //角色名称
    private String roleDesc;                          //角色描述
    private Boolean available = Boolean.FALSE;//角色是否可用

}
