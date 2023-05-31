package com.zhou.community.service;

import com.zhou.community.entity.User;

import java.util.Map;

public interface UserService {
    User selectById(int id);

    Map<String, Object> register(User user);

    int activation(int userId, String code);
}
