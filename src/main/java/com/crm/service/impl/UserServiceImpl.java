package com.crm.service.impl;

import com.crm.mapper.UserLogMapper;
import com.crm.mapper.UserMapper;
import com.crm.pojo.Role;
import com.crm.pojo.User;
import com.crm.pojo.UserLog;
import com.crm.shiro.ShiroUtil;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLogMapper userLogMapper;

    @Override
    public User findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }

    /**
     * 获取所有角色
     * @return
     */

    @Override
    public List<Role> findAllRole() {
        return userMapper.findAllRole();
    }

    /**
     * 根据查询参数获取用户列表
     * @param params
     * @return
     */
    @Override
    public List<User> findUserListByParam(Map<String, Object> params) {
        return userMapper.findByParam(params);
    }

    /**
     * 查询用户总数
     * @return
     */
    @Override
    public Long findUserCount() {
        return userMapper.count();
    }

    /**
     * 根据查询条件获取用户数量
     * @param params
     * @return
     */
    @Override
    public Long findUserCountByParams(Map<String, Object> params) {
        return userMapper.countByParams(params);
    }

    /**
     * 保存新用户
     * @param user
     */
    @Override
    public void saveUser(User user) {
        user.setEnable("1");
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        //TODO 微信注册

        userMapper.save(user);
    }

    @Override
    public User findUserByUserName(String username) {
        return userMapper.findByUserName(username);
    }

    /**
     * 重置密码
     * @param id
     */
    @Override
    public void resetUserPassword(Integer id) {
        User user = userMapper.findById(id);
        if(user != null){
            user.setPassword(DigestUtils.md5Hex("000000"));
            userMapper.updateUser(user);
        }
    }

    @Override
    public User findUserById(Integer id) {
        return userMapper.findById(id);
    }

    /**
     * 编辑用户
     * @param user
     */
    @Override
    public void editUser(User user) {
        userMapper.updateUser(user);
    }

    /**
     * 修改用户密码
     * @param password
     */
    public void changePassword(String password) {
        User user = ShiroUtil.getCurrentUser();
        user.setPassword(DigestUtils.md5Hex(password));

        userMapper.updateUser(user);
    }
    /**
     * 获取当前登录用户的登录日志
     * @param start
     * @param length
     * @return
     */
    public List<UserLog> findCurrentUserLog(String start, String length) {
        Map<String,Object> param = Maps.newHashMap();
        param.put("userId",ShiroUtil.getCurrentUserId());
        param.put("start",start);
        param.put("length",length);
        return userLogMapper.findByParam(param);
    }

    /**
     * 获取当前登录用户的日志数量
     * @return
     */
    public Long findCurrentUserLogCount() {
        Map<String,Object> param = Maps.newHashMap();
        param.put("userId",ShiroUtil.getCurrentUserId());
        return userLogMapper.countByParam(param);
    }
}
