package com.crm.controller;


import com.crm.exception.NotFoundException;
import com.crm.pojo.User;
import com.crm.service.UserService;
import com.crm.shiro.ShiroUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 修改密码
     */
    @GetMapping("/password")
    public String editPassword() {
        return "setting/password";
    }
    @PostMapping("/password")
    @ResponseBody
    public String editPassword(String password) {
        userService.changePassword(password);
        return "success";
    }

    /**
     * 验证原始密码是否正确
     */
    @GetMapping("/validate/password")
    @ResponseBody
    public String validateOldPassword(@RequestHeader("X-Requested-With") String xRequestedWith,
                                      String oldpassword) {
        if("XMLHttpRequest".equals(xRequestedWith)) {
            User user = ShiroUtil.getCurrentUser();
            if(user.getPassword().equals(DigestUtils.md5Hex(oldpassword))) {
                return "true";
            }
            return "false";
        } else {
            throw new NotFoundException();
        }
    }

}
