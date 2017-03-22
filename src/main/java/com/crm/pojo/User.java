package com.crm.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {


    private Integer id;
    private String username;
    private String password;
    private String realname;
    private String weixin;
    private Timestamp createtime;
    private String enable;
    private Integer roleid;



}
