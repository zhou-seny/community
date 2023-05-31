package com.zhou.community.controller;

import com.github.pagehelper.PageInfo;
import com.zhou.community.dao.DiscussPostMapper;
import com.zhou.community.entity.DiscussPost;
import com.zhou.community.entity.Page;
import com.zhou.community.entity.User;
import com.zhou.community.service.DiscussPostService;
import com.zhou.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        page.setRows(discussPostService.discussPostCount(0));
        page.setPath("/index");

        List<DiscussPost> discussPosts = discussPostService.selectDiscussPost(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> list = new ArrayList<>();

        if (discussPosts!=null){
            for (DiscussPost post : discussPosts){
                Map<String, Object> map = new HashMap<>();
                User user = userService.selectById(post.getUserId());
                map.put("post", post);
                map.put("user", user);
                list.add(map);
            }
        }
        model.addAttribute("discussPosts", list);
        return "index";
    }


//    /**
//     * 利用插件PageHelper进行分页
//     * @param model
//     * @param pageNum
//     * @return
//     */
//    @RequestMapping(path = "/index", method = RequestMethod.GET)
//    public String getIndexPage(Model model, int pageNum){
//
//        //List<DiscussPost> discussPosts = discussPostService.selectDiscussPost(0, page.getOffset(), page.getLimit());
//        PageInfo<DiscussPost> pageInfo = discussPostService.byPageHelper(0, pageNum, 10);
//        List<DiscussPost> discussPosts = pageInfo.getList();
//        //int currentPage = pageInfo.getPageNum();
//        long total = pageInfo.getTotal();
//        int pages = pageInfo.getPages();
//
//        List<Map<String, Object>> list = new ArrayList<>();
//
//        if (discussPosts!=null){
//            for (DiscussPost post : discussPosts){
//                Map<String, Object> map = new HashMap<>();
//                User user = userService.selectById(post.getUserId());
//                map.put("post", post);
//                map.put("user", user);
//                list.add(map);
//            }
//        }
//        model.addAttribute("discussPosts", list);
//        model.addAttribute("cur", pageNum);
//        model.addAttribute("total", total);
//        model.addAttribute("pages", pages);
//        return "index";
//    }

    @RequestMapping(path = "/detail", method = RequestMethod.GET)
    public String getDetail(Model model, int userId){
//        page.setPath("/detail");
//        page.setRows(discussPostService.discussPostCount(userId));
//
//        List<DiscussPost> discussPosts = discussPostService.selectDiscussPost(userId, page.getCurrent(), page.getLimit());
        System.out.println();
        return "site/profile";
    }

}
