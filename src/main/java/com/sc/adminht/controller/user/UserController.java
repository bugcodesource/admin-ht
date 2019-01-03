package com.sc.adminht.controller.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sc.adminht.controller.BaseController;
import com.sc.adminht.entity.system.UserInfo;
import com.sc.adminht.service.system.AdminUserService;
import com.sc.adminht.utils.BaseResult;
import com.sc.adminht.utils.Pages;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NariT
 * @create 2018-12-24 17:17
 * @desc
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    private final AdminUserService adminUserService;

    @Autowired
    public UserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @RequestMapping("/{suffix}")
    public String showUserBySuffix(@PathVariable String suffix, ModelMap modelMap) {
        String suf = suffix.toUpperCase();
        log.debug("页面跳转---->开始");
        modelMap.addAttribute("page_title", suf + ":用户信息列表");
        modelMap.addAttribute("title", "Hello " + suf + "!!!");
        return "showUser";
    }

    @ResponseBody
    @RequestMapping(value = "/showUser", method = RequestMethod.GET)
    @RequiresPermissions("userInfo:view")
    public Map<String, Object> apiGetShowUser(HttpServletRequest request) {
        return showUser(request,"GET");
    }
    @ResponseBody
    @RequestMapping(value = "/showUser", method = RequestMethod.POST)
    public Map<String, Object> apiPostShowUser(HttpServletRequest request) {
        return showUser(request,"POST");
    }
    @RequiresPermissions("userInfo:view")
    public Map<String, Object> showUser(HttpServletRequest request, String method) {
        Map<String, Object> pages = new HashMap<>();
        log.debug(method+"查询所有用户信息");

        String limit = request.getParameter("limit");
        log.debug(limit+"条数");
        String nowPage = request.getParameter("nowPage");
        log.debug(nowPage+"页数");
        String search = request.getParameter("search");
        log.debug("内容："+search);

        int nowPaged = Integer.parseInt(null == nowPage ? "1" :nowPage);
        int limitd = Integer.parseInt(null == limit ? "10" : limit);

        PageHelper.startPage(nowPaged,limitd);
        if (null == search && "".equals(search)) {
            List<UserInfo> userList = adminUserService.queryAllList();
            //分页处理
            PageInfo<UserInfo> pageInfo = new PageInfo<>(userList);
            //设置记录页面数据条数记录
            int total = (int) pageInfo.getTotal();
            pages = this.setPageJson(true,"共查询出"+total+"条数据！",0,total,userList);
        } else {
            List<UserInfo> userList = adminUserService.queryBySearch(search);
            //分页处理
            PageInfo<UserInfo> pageInfo = new PageInfo<>(userList);
            //设置记录页面数据条数记录
            int total = (int) pageInfo.getTotal();
            pages = this.setPageJson(true,"共查询出"+total+"条数据！",0,total,userList);
        }
        return pages;
    }
    /**
     * <p> 修改账号状态 </p>
     * @author: NariT <br>
     * @data: 2018/12/26 13:54 <br>
     * @param:  userId 用户id status 账号状态码 <br>
     * @return  <br>
     */
    @RequestMapping(value = "/updateStatus/{userId}/{status}",method = RequestMethod.POST)
    @RequiresPermissions("update:status")
    @ResponseBody
    public Map<String, Object> updateStatus(@PathVariable("userId") int userId, @PathVariable("status") int status) {
        Map<String, Object> json = new HashMap<>();
        log.debug("获取ID为"+userId+"的用户");
        log.debug(String.format("获取用户的状态为%d", status));
        try {
            if (status == 1) {
            int userStatus = 0;
            adminUserService.updateStatus(userStatus,userId);
            json = this.setJson(true,"解除冻结",null);
        } else if (status == 0) {
            int userStatus = 1;
            adminUserService.updateStatus(userStatus,userId);
            json = this.setJson(true,"冻结成功",null);
        }
        } catch (Exception e) {
            json = this.setJson(false,"异常",null);
            log.error("修改状态码失败",e);
        }
        return json;
    }
    /**
     * <p> 删除用户信息 </p>
     * @author: NariT <br>
     * @data: 2018/12/26 13:53 <br>
     * @param: userId  <br>
     * @return Map<String, Object> <br>
     */
    @RequestMapping("/delUser/{userId}")
    @RequiresPermissions("userInfo:del")
    @ResponseBody
    public Map<String, Object> delUser(@PathVariable("userId") Integer userId) {
        Map<String, Object> json = new HashMap<>();
        try {
            adminUserService.deleteUserInfo(userId);
            json = this.setJson(true, "删除成功", null);
        } catch (Exception e) {
            this.setAjaxException(json);
            log.error("删除用户异常",e);
        }
        return json;
    }
}
