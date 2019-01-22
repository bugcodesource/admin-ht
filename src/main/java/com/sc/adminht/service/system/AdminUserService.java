package com.sc.adminht.service.system;

import com.sc.adminht.entity.system.AdminUser;
import com.sc.adminht.entity.system.UserInfo;
import com.sc.adminht.entity.user.Address;
import com.sc.adminht.mapper.system.AdminUserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


public interface AdminUserService {

    public AdminUser findUserByName(@Param("loginName") String loginName);


    //public Map<String, Object> pageShow(int limit,int offset);
    public List<UserInfo> queryAllList();
    public int count(Map<String, Object> map);
    //修改用户账号状态
    public int updateStatus(int status,int userId);

    public int deleteUserInfo(int userId);

    List<UserInfo> queryBySearch(String search);

    int createAddress(Address address);
}
