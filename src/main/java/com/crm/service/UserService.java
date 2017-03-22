package com.crm.service;


import com.crm.pojo.Role;
import com.crm.pojo.User;
import com.crm.pojo.UserLog;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findByUserName(String userName);

    List<Role> findAllRole();

    List<User> findUserListByParam(Map<String, Object> params);

    Long findUserCount();

    Long findUserCountByParams(Map<String, Object> params);

    void saveUser(User user);

    User findUserByUserName(String username);

    void resetUserPassword(Integer id);

    User findUserById(Integer id);

    void editUser(User user);

    void changePassword(String password);

    List<UserLog> findCurrentUserLog(String start, String length);

    Long findCurrentUserLogCount();

    Role findRoleByRoleId(Integer roliId);


    void saveUserLogin(String remoteIp);

}
