package com.sc.adminht.mapper.system;

import com.sc.adminht.entity.system.AdminUser;
import com.sc.adminht.entity.system.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p> AdminUserMapper </p>
 * @author: NariT <br>
 * @data: 2018/12/28 13:57 <br>
 */
@Mapper
@Repository
public interface AdminUserMapper {
    /**
     * <p> 根据用户名查询用户信息，用于登录 </p>
     * @author: NariT <br>
     * @data: 2018/12/28 13:55 <br>
     * @param loginName 登录名 <br>
     * @return loginName
     */
    AdminUser findUserByName(@Param("loginName") String loginName);
    /**
     * <p> 查询用户信息列表 </p>
     * @author: NariT <br>
     * @data: 2018/12/28 13:53 <br>
     * @return List<UserInfo>
     */
    List<UserInfo> queryAllList();

    /**
     * @Description：<p>获取数据条数</p>
     * @Param ：map：<br/>
     * @Return：int <br/>
     */
    int count(Map<String, Object> map);

    /**
     * <p> 修改用户账号状态 </p>
     * @author: NariT <br>
     * @data: 2018/12/28 13:42 <br>
     * @param  userId 用户id <br>
     * @param status 状态值(0、正常；1、冻结) <br>
     * @return userId status
     */
    int updateStatus(@Param("status") int status, @Param("userId") int userId);

    /**
     * <p> 删除用户 </p>
     * @author: NariT <br>
     * @data: 2018/12/28 13:46 <br>
     * @param userId 用户id <br>
     * @return userId <br>
     */
    int deleteUserInfo(@Param("userId") int userId);
    /**
     * @Description：<p>分页查询</p>
     * @Param ：search<br/>
     * @Return：java.util.List<com.sc.adminht.entity.system.UserInfo> <br/>
     */
    List<UserInfo> queryBySearch(@Param("search") String search);
}
