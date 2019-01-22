package com.sc.adminht.controller.system;

import com.sc.adminht.controller.BaseController;
import com.sc.adminht.entity.user.Address;
import com.sc.adminht.service.system.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author NariT
 * @create 2018-12-17 16:02
 * @desc
 */
@Slf4j
@Controller
@RequestMapping("/userInfo1")
public class AdminUserController extends BaseController {
    @Autowired
    private AdminUserService adminUserService;

    private String province;
    private String city;
    private String county;
    private String town;
    private String village;

    @RequestMapping(value = "/userList",method = RequestMethod.GET)
    @RequiresPermissions("userInfo:view")
    public String userInfo() {
        return "showUser";
    }
    /**
     * 用户查询.
     * @return
     */
    /*//@ResponseBody
    @GetMapping(value = "/userList",produces = "application/json;charset=UTF-8")
    @RequiresPermissions("userInfo:view")//权限管理;
    //@ResponseBody
    public @ResponseBody ModelAndView userInfo(@RequestParam Map<String, Object> map){
        ModelAndView model = new ModelAndView();
        List<UserInfo> userList = adminUserService.queryUserInfo(map);
        int total = userList.size();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rows",userList);
        //PageUtils pageUtils = new PageUtils(userList, total);
        model.addObject("total",total);
        model.addObject("rows",jsonObject);
        model.setViewName("userList1");
        return model;
    }*/
    /*@GetMapping("/userList")
    @RequiresPermissions("userInfo:view")//权限管理;
    public String userInfo(UserInfo userInfo,Model model) {
        List<UserInfo> list = adminUserService.queryUserInfo();
        model.addAttribute("adminUsers",list);
        return "userList1";
    }*/
    /*@RequestMapping("/userList")
    @RequiresPermissions("userInfo:view")
    public @ResponseBody PageUtils list(Integer limit,Integer offset) {
        log.info();
        //Page<UserInfo> page = PageHelper.offsetPage(offset,limit);
        List<UserInfo> userInfos = adminUserService.queryUserInfo();
        return new PageUtils(2,userInfos);
    }*/
    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")
    public void userInfoAdd(Address address){

        String address1 = "湖北省武汉市洪山县海损镇45组35号";
        for (Map<String, String> m : stringToAddress(address1)) {
            province = m.get("province");
            city = m.get("city");
            county = m.get("county");
            town = m.get("town");
            village = m.get("village");
        }
        address.setProvince(province);
        address.setCity(city);
        address.setCounty(county);
        address.setTown(town);
        address.setVillage(village);
        adminUserService.createAddress(address);
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel(){
        return "userInfoDel";
    }

}
