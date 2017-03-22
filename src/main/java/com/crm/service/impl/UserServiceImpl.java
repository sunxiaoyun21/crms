package com.crm.service.impl;

import com.crm.mapper.RoleMapper;
import com.crm.mapper.UserLogMapper;
import com.crm.mapper.UserMapper;
import com.crm.pojo.Role;
import com.crm.pojo.User;
import com.crm.pojo.UserLog;
import com.crm.service.UserService;
import com.crm.shiro.ShiroUtil;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLogMapper userLogMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    /**
     * 创建用户的IP
     * @param IP
     */
    @Override
    public void saveUserLogin(String IP) {
        UserLog userLog=new UserLog();
        userLog.setLoginip(IP);
        userLog.setUserid(ShiroUtil.getCurrentUserId());
        userLog.setLogintime(DateTime.now().toString("YYYY-MM-dd HH:mm"));
        userLogMapper.save(userLog);

    }

    @Override
    public Role findRoleByRoleId(Integer roliId) {
        return roleMapper.findRoleById(roliId);
    }
}
