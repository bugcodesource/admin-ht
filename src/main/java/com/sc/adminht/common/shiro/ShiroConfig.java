package com.sc.adminht.common.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p> Shiro配置类 </p>
 * @author: NariT <br>
 * @data: 2018/12/28 13:31 <br>
 */
@Slf4j
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/**/*.css", "anon");
        filterChainDefinitionMap.put("/**/*.js", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/img/**", "anon");
        filterChainDefinitionMap.put("/flags/**/*.svg", "anon");
        filterChainDefinitionMap.put("/userInfoList", "perms[userInfo/userAdd]");
        filterChainDefinitionMap.put("/**", "authc");
        //authc表示需要验证身份才能访问，还有一些比如anon表示不需要验证身份就能访问等。
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * <p>  </p>
     * @author: NariT <br>
     * @data: 2018/12/28 13:29 <br>
     * @param:   <br>
     * @doc SecurityManager 是 Shiro 架构的核心，通过它来链接Realm和用户(文档中称之为Subject.)  <br>
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入缓存对象。
        securityManager.setCacheManager(ehCacheManager());
        //注入rememberMeManager;
        securityManager.setRememberMeManager(cookieRememberMeManager());
        //将Realm注入到SecurityManager中。
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        //设置解密规则
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    /**
     * <p> 因为我们的密码是加过密的，所以，如果要Shiro验证用户身份的话，需要告诉它我们用的是md5加密的， </p>
     * <p> 并且是加密了两次。同时我们在自己的Realm中也通过SimpleAuthenticationInfo返回了加密时使用的盐。 </p>
     * <p> 这样Shiro就能顺利的解密密码并验证用户名和密码是否正确了。 </p>
     * @author: NariT <br>
     * @data: 2018/12/28 13:30 <br>
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //散列的次数，比如散列两次，相当于 md5(md5(""));
        hashedCredentialsMatcher.setHashIterations(2);
        return hashedCredentialsMatcher;
    }
    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @return securityManager
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }
    @Bean
    public SimpleMappingExceptionResolver resolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "/403");
        resolver.setExceptionMappings(properties);
        return resolver;
    }
    @Bean
    public EhCacheManager ehCacheManager() {
        log.info(" ===> ShiroConfiguration.getEhCacheManager()");
        EhCacheManager ehCacheManager = new EhCacheManager();
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehCacheManager;
    }
    /**
     * <p> cookie对象 </p>
     * @author: NariT <br>
     * @data: 2018/12/28 13:28 <br>
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        log.info(" ===> ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    /**
     * <p> cookie管理对象 </p>
     * @author: NariT <br>
     * @data: 2018/12/28 13:28 <br>
     * @param:   <br>
     * @return  <br>
     */
    @Bean
    public CookieRememberMeManager cookieRememberMeManager() {
        log.info(" ===> ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager manager = new CookieRememberMeManager();
        manager.setCookie(rememberMeCookie());
        return manager;
    }
}

