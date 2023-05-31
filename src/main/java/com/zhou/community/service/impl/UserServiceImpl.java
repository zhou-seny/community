package com.zhou.community.service.impl;

import com.zhou.community.dao.UserMapper;
import com.zhou.community.entity.User;
import com.zhou.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User selectById(int id) {
        return userMapper.selectById(id);
    }


}
