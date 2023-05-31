package com.zhou.community.controller;

import com.zhou.community.entity.User;
import com.zhou.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/getUser")
    @ResponseBody
    public List<User> getUser(int id){
        List<User> list = new ArrayList<>();
        User user = userService.selectById(id);
        list.add(user);
        return list;
    }
}
