package com.sc.adminht.entity.system;

import lombok.Data;

@Data
public class SysPermission {
    private Integer id;//权限id
    private String name;//权限名称
    private String url;//资源路径
    private  String permission;//权限字符串
}
