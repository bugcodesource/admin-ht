package com.sc.adminht.entity.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p> 后台用户实体类 </p>
 * @author: SC <br>
 * @data: 2018/12/17 16:10 <br>
 * @version : 1.0  <br>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUser {

    /**
     *用户id
     */
    private int userId;
    /**
     * 用户登录名
     */
    private String loginName;
    /**
     * 用户登录密码
     */
    private String loginPwd;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 账号创建时间
     */
    private String createTime;
    /**
     * 用户头像
     */
    private String imgUrl;
    /**
     * 用户邮箱地址
     */
    private String email;
    /**
     * 用户电话
     */
    private String telPhone;
    /**
     * 用户角色id
     */
    private int roleId;
    /**
     * 用户账号状态（0 正常;1 冻结;2 删除）
     */
    private int status;
    /**
     * 用户登录ip
     */
    private String loginIp;
    /**
     * 密码盐
     */
    private String salt;
    /**
     * 密码盐
     */
    public String getCredentialsSalt() {
        return this.loginName+this.salt;
    }
}
