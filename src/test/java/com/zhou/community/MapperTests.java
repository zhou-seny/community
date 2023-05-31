package com.zhou.community;

import com.zhou.community.dao.DiscussPostMapper;
import com.zhou.community.dao.UserMapper;
import com.zhou.community.entity.DiscussPost;
import com.zhou.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        User liubei = userMapper.selectByName("liubei");
        System.out.println(liubei);

        User email = userMapper.selectByEmail("nowcoder101@sina.com");
        System.out.println(email);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("炎龙");
        user.setPassword("123456");
        user.setSalt("123456");
        user.setCreateTime(new Date());
        user.setEmail("test@qq.com");
        user.setHeaderUrl("http://www.baidu.com");
        int i = userMapper.insertUser(user);
        System.out.println(i);
    }

    @Test
    public void testUpdate(){
        int i = userMapper.updateStatus(150, 1);
        System.out.println(i);

        int i1 = userMapper.updatePassword(150, "1234567");
        System.out.println(i1);

        int i2 = userMapper.updateHeader(150, "http://www.baidu.com.org");
        System.out.println(i2);
    }

    @Test
    public void testDiscussPost(){
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPost(101, 2, 10);
        for (DiscussPost d : discussPosts){
            System.out.println(d);
        }
    }

    @Test
    public void testDiscussPostCount(){
        int i = discussPostMapper.discussPostCount(101);
        System.out.println(i);
    }
}
