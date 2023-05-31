package com.zhou.community.service.impl;

import com.zhou.community.dao.UserMapper;
import com.zhou.community.entity.User;
import com.zhou.community.service.UserService;
import com.zhou.community.utils.CommunityConstant;
import com.zhou.community.utils.CommunityUtils;
import com.zhou.community.utils.MailClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService, CommunityConstant {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public User selectById(int id) {
        return userMapper.selectById(id);
    }

    @Override
    public Map<String, Object> register(User user) {
        Map<String, Object> map = new HashMap<>();
        if (user==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        if (StringUtils.isBlank(user.getUsername())){
            map.put("usernameMsg", "用户名不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())){
            map.put("passwordMsg", "密码不能为空");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail())){
            map.put("emailMsg", "邮箱不能为空");
            return map;
        }

        User u = userMapper.selectByName(user.getUsername());
        if (u!=null){
            map.put("usernameMsg", "用户名已经存在");
            return map;
        }
        u = userMapper.selectByEmail(user.getEmail());
        if (u!=null){
            map.put("emailMsg", "邮箱已经被注册");
            return map;
        }

        //条件满足，存入数据库
        user.setType(0);
        user.setStatus(0);
        user.setSalt(CommunityUtils.generateUUID().substring(0, 5));
        user.setPassword(CommunityUtils.md5(user.getPassword() + user.getSalt()));
        user.setCreateTime(new Date());
        user.setHeaderUrl(String.format("https://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));
        user.setActivationCode(CommunityUtils.generateUUID());
        userMapper.insertUser(user);

        //发送邮件+激活邮件
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        String url = domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        String process = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活邮件", process);

        return map;
    }

    @Override
    public int activation(int userId, String code) {
        User user = userMapper.selectById(userId);
        if (user.getActivationCode().equals(code)){
            userMapper.updateStatus(userId, 1);
            return CommunityConstant.ACTINVATION_SUCCESS;
        } else if (user.getStatus()==1){
            return CommunityConstant.ACTINVATION_REPEAT;
        } else {
            return CommunityConstant.ACTINVATION_FAIL;
        }
    }


}
