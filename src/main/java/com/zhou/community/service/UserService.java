package com.zhou.community.service;

import com.zhou.community.entity.LoginTicket;
import com.zhou.community.entity.User;

import java.util.Map;

public interface UserService {
    User selectById(int id);

    Map<String, Object> register(User user);

    int activation(int userId, String code);

    Map<String, Object> login(String username, String password, int expired);

    void logout(String ticket);

    LoginTicket selectByTicket(String ticket);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);
}
