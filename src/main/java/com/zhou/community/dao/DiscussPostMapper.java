package com.zhou.community.dao;

import com.zhou.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {
    List<DiscussPost> byPageHelper(int userId);

    List<DiscussPost> selectDiscussPost(int userId, int offset, int limit);

    // 动态sql，必须使用注解@Param进行改名
    int discussPostCount(@Param("userId") int userId);
}
