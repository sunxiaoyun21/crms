package com.crm.mapper;


import com.crm.pojo.User;

public interface UserMapper {

    User findByUserName(String userName);
}
