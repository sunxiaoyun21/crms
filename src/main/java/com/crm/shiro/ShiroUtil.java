package com.crm.shiro;

import com.crm.pojo.User;
import org.apache.shiro.SecurityUtils;


public class ShiroUtil {

    public static User  getCurrentUser(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    public static String getCurrentRealName(){
       return getCurrentUser().getRealname();
    }

    public static Integer getCurrentUserId(){
        return getCurrentUser().getId();
    }

    public static String getCurrentUserName(){
        return getCurrentUser().getUsername();
    }


}
