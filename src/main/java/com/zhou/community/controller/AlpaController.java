package com.zhou.community.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AlpaController {

    @RequestMapping("/http")
    public void get(HttpServletRequest request, HttpServletResponse response){
        String code = request.getParameter("code");
        System.out.println(code);

        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter();
                ){
            writer.write("你好");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(path = "/students", method = RequestMethod.GET)
    @ResponseBody
    public String getStudents(@RequestParam(name = "name", required = false, defaultValue = "里斯") String name,
                             @RequestParam(name = "pwd", required = false, defaultValue = "123") String pwd){
        System.out.println(name);
        System.out.println(pwd);
        return "你好";
    }

    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id){
        System.out.println(id);
        return "a sutdnet";
    }

    //Post请求提交数据的获取依然是String name, String pwd

    //返回html页面1
    @RequestMapping(path = "/hx", method = RequestMethod.GET)
    public ModelAndView getHx(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("name", "张三");
        modelAndView.addObject("age", "20");
        modelAndView.setViewName("/demo/view");
        return modelAndView;
    }

    //返回html页面2
    @RequestMapping(path = "/h", method = RequestMethod.GET)
    public String getH(Model model){
        model.addAttribute("name", "张三");
        model.addAttribute("age", "20");
        return "/demo/view";
    }

    //返回json数据
    @RequestMapping(path = "/json", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> getJson(){
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhourunfa");
        map.put("age", "120");
        return map;
    }

    //返回json数据(多个)
    @RequestMapping(path = "/jsons", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, String>> getJsons(){
        List<Map<String, String>> list = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhourunfa");
        map.put("age", "120");
        list.add(map);
        Map<String, String> map2 = new HashMap<>();
        map2.put("name", "zhourunfaaaaaaaaaa");
        map2.put("age", "120aaaaaaaaaaaaaaaa");
        list.add(map2);
        return list;
    }
}
