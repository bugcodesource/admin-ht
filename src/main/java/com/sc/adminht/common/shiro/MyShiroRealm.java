package com.sc.adminht.common.shiro;


import com.sc.adminht.entity.system.AdminUser;
import com.sc.adminht.mapper.system.SysPermissionMapper;
import com.sc.adminht.mapper.system.SysRoleMapper;
import com.sc.adminht.service.system.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p> Shiro实现登录验证类 </p>
 * @author: NariT <br>
 * @data: 2018/12/28 13:23 <br>
 */
@Slf4j
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private AdminUserService adminUserService;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {

        log.debug("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        AdminUser adminUser  = (AdminUser)principal.getPrimaryPrincipal();
        log.info("------------>" + principal.getPrimaryPrincipal());

        sysRoleMapper.findRoleByName(adminUser.getLoginName()).forEach(
                sysRole -> {
                    authorizationInfo.addRole(sysRole.getRole());
                    sysPermissionMapper.findPermByRoleId(sysRole.getRoleId()).forEach(
                            sysPermission -> authorizationInfo.addStringPermission(sysPermission.getPermission())
                    );
                }
        );
        log.info("用户" + adminUser.getLoginName() + "具有的角色:" + authorizationInfo.getRoles());
        log.info("用户" + adminUser.getLoginName()  + "具有的权限：" + authorizationInfo.getStringPermissions());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        log.info(" ===> MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String loginName = (String)token.getPrincipal();
        log.info(String.valueOf(token.getCredentials()));
        //通过adminUser从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        AdminUser adminUser = adminUserService.findUserByName(loginName);
        log.info("----->>userInfo=",adminUser);
        if(adminUser == null) {
            throw new UnknownAccountException();
        }
        if(adminUser.getStatus() == 1) {
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                //用户名
                adminUser,
                //密码
                adminUser.getLoginPwd(),
                //加盐 盐：salt=username+salt
                ByteSource.Util.bytes(adminUser.getCredentialsSalt()),
                //realm name
                getName()
        );
        return authenticationInfo;
    }
}
