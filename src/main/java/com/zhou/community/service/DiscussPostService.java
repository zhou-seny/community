package com.zhou.community.service;

import com.github.pagehelper.PageInfo;
import com.zhou.community.entity.DiscussPost;

import java.util.List;

public interface DiscussPostService {
    PageInfo<DiscussPost> byPageHelper(int userId, int pageNum, int pageSize);

    List<DiscussPost> selectDiscussPost(int userId, int offset, int limit);
    int discussPostCount(int userId);
}
