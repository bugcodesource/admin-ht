package com.sc.adminht.service.impl;

import com.sc.adminht.entity.system.AdminUser;
import com.sc.adminht.entity.system.UserInfo;
import com.sc.adminht.entity.user.Address;
import com.sc.adminht.mapper.system.AdminUserMapper;
import com.sc.adminht.mapper.user.AddressMapper;
import com.sc.adminht.service.system.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author NariT
 * @create 2018-12-18 9:58
 * @desc
 */
@Service
@Slf4j
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AdminUser findUserByName(String loginName) {
        return adminUserMapper.findUserByName(loginName);
    }


    /*@Override
    public List<UserInfo> queryUserInfo() {
        return adminUserMapper.queryUserInfo();
    }*/

    @Override
    public int count(Map<String, Object> map) {
        return adminUserMapper.count(map);
    }

    @Override
    public List<UserInfo> queryAllList() {
        return adminUserMapper.queryAllList();
    }
    //修改用户账号状态
    @Override
    public int updateStatus(int status, int userId) {
        return adminUserMapper.updateStatus(status,userId);
    }
    //删除用户信息
    @Override
    public int deleteUserInfo(int userId) {
        return adminUserMapper.deleteUserInfo(userId);
    }

    @Override
    public List<UserInfo> queryBySearch(String search) {
        return adminUserMapper.queryBySearch(search);
    }

    @Override
    public int createAddress(Address address) {
        return addressMapper.createAddress(address);
    }
}
