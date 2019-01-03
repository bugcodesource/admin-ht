package com.sc.adminht.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author NariT
 * @create 2018-12-20 15:06
 * @desc
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token,
                                     Subject subject,
                                     ServletRequest request,
                                     ServletResponse response) throws Exception {
        String successUrl = "/index";
        WebUtils.issueRedirect(request, response, successUrl);
        return false;

    }
}
