package com.crm.mapper;


import com.crm.pojo.Role;
import com.crm.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    User findByUserName(String userName);

    List<Role> findAllRole();

    List<User> findByParam(Map<String, Object> params);

    Long count();

    Long countByParams(Map<String, Object> params);

    void save(User user);

    User findById(Integer id);

    void updateUser(User user);
}
