package com.crm.shiro;

import com.crm.pojo.User;
import com.crm.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class ShiroDbRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 权限认证
     * @param principalCollection
     * @return
     */

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken= (UsernamePasswordToken) authenticationToken;
        String userName=usernamePasswordToken.getUsername();
        User user=userService.findByUserName(userName);
        if(user !=null){
            return  new  SimpleAuthenticationInfo(user, user.getPassword(),getName());
        }
        return null;
    }
}
