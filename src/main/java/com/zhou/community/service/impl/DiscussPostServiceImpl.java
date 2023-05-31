package com.zhou.community.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhou.community.dao.DiscussPostMapper;
import com.zhou.community.entity.DiscussPost;
import com.zhou.community.service.DiscussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class DiscussPostServiceImpl implements DiscussPostService {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Override
    public PageInfo<DiscussPost> byPageHelper(int userId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<DiscussPost> discussPosts = discussPostMapper.byPageHelper(userId);
        PageInfo<DiscussPost> pageInfo = new PageInfo<>(discussPosts);
        System.out.println(pageInfo);
        System.out.println(pageInfo.getList());
        return pageInfo;
    }

    @Override
    public List<DiscussPost> selectDiscussPost(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscussPost(userId, offset, limit);
    }

    @Override
    public int discussPostCount(int userId) {
        return discussPostMapper.discussPostCount(userId);
    }
}
