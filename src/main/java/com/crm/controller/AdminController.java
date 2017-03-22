package com.crm.controller;

import com.crm.dto.DataTablesResult;
import com.crm.dto.JSONResult;
import com.crm.pojo.Role;
import com.crm.pojo.User;
import com.crm.service.UserService;
import com.crm.util.Strings;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String userList(Model model){
        List<Role> roleList = userService.findAllRole();
        model.addAttribute("roleList",roleList);
        return "admin/userlist";
    }

    @GetMapping("/users/load")
    @ResponseBody
    public DataTablesResult<User> loadUsers(HttpServletRequest request){
        String draw = request.getParameter("draw");
        String start = request.getParameter("start");
        String length = request.getParameter("length");
        String keyword = request.getParameter("search[value]");
        keyword = Strings.toUTF8(keyword);

        Map<String,Object> params = Maps.newHashMap();
        params.put("keyword",keyword);
        params.put("start",start);
        params.put("length",length);

        List<User> userList = userService.findUserListByParam(params);
        Long count = userService.findUserCount();
        Long filterCount = userService.findUserCountByParams(params);

        return new DataTablesResult<>(draw,userList,count,filterCount);
    }

    /**
     * 验证用户名是否可用
     * @param username
     * @return
     */
    @RequestMapping(value = "/user/checkusername",method = RequestMethod.GET)
    @ResponseBody
    public String checkUserName(String username) {
        User user = userService.findUserByUserName(username);
        if(user == null) {
            return "true";
        }
        return "false";
    }

    /**
     * 添加新用户
     * @param user
     * @return
     */
    @PostMapping("/user/new")
    @ResponseBody
    public String saveUser(User user){
        userService.saveUser(user);
        return "success";
    }


    /**
     * 重置密码
     * @return
     */
    @PostMapping("/users/resetpassword")
    @ResponseBody
    public String resetPassword(Integer id) {
        userService.resetUserPassword(id);
        return "success";
    }

    /**
     * 根据用户的ID显示用户JSON
     * @return
     */
    @RequestMapping(value = "/users/{id:\\d+}.json",method = RequestMethod.GET)
    @ResponseBody
    public JSONResult showUser(@PathVariable Integer id) {
        User user = userService.findUserById(id);
        if(user == null) {
            return new JSONResult("找不到"+id+"对应的用户");
        } else {
            return new JSONResult(user);
        }
    }

    /**
     * 编辑用户
     * @param user
     * @return
     */
    @PostMapping("/users/edit")
    @ResponseBody
    public String editUser(User user) {
        userService.editUser(user);
        return "success";
    }

}
