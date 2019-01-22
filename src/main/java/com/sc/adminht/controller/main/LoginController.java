package com.sc.adminht.controller.main;

import com.sc.adminht.controller.BaseController;
import com.sc.adminht.entity.system.AdminUser;
import com.sc.adminht.service.system.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author NariT
 * @create 2018-12-20 13:11
 * @desc
 */
@Controller
@Slf4j
public class LoginController extends BaseController {

    private final AdminUserService adminUserService;

    @Autowired
    public LoginController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @RequestMapping(value = {"/","/index.html"})
    public String index(){
        return "/admin/index.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login.html";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST )
    @ResponseBody
    public  Map<String, Object> login(HttpServletRequest request,@RequestBody AdminUser adminUser) {
        Map<String, Object> json = new HashMap<>();
        String loginIp = getServerIpAddress();
        if (null == adminUser.getLoginName() || adminUser.getLoginName().isEmpty()) {
            json = this.setJson(false,"确定是否创建账号",null);
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(adminUser.getLoginName(),adminUser.getLoginPwd());
        AdminUser adminUser1 = adminUserService.findUserByName(adminUser.getLoginName());
        try {
            subject.login(token);
            request.getSession().setAttribute("user",adminUser1);
            json = this.setJson(true,"登录成功",null);

        } catch (LockedAccountException le) {
            token.clear();
            json = this.setJson(false,"用户账号冻结",null);
        } catch (AuthenticationException e) {
            token.clear();
            json = this.setJson(false,"用户名或密码不正确",null);
        } catch (Exception e) {
            json = this.setJson(false,"异常",null);
            log.error("登录异常",e);
        }
        return json;
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "403";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        String user = "user";
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1000*60*60);
        //取出当前验证主体
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            //不为空，执行一次logout的操作，将session全部清空
            subject.logout();
            if ((session.getAttribute(user) != null) && session.getId().equals(session.getAttribute(user))) {
                session.removeAttribute(user);
            }
        }
        return "login1";
    }
}
