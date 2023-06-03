package com.zhou.community.controller;

import com.zhou.community.annotation.LoginRequired;
import com.zhou.community.entity.User;
import com.zhou.community.service.UserService;
import com.zhou.community.utils.CommunityUtils;
import com.zhou.community.utils.HostHolder;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Value("${community.path.upload}")
    private String uploadPath;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${community.path.domain}")
    private String domain;

    @Autowired
    private HostHolder hostHolder;

    @RequestMapping("/getUser")
    @ResponseBody
    public List<User> getUser(int id) {
        List<User> list = new ArrayList<>();
        User user = userService.selectById(id);
        list.add(user);
        return list;
    }

    @LoginRequired
    @RequestMapping(path = "/setting", method = RequestMethod.GET)
    public String getSetingPage() {
        return "/site/setting";
    }

    @LoginRequired
    @RequestMapping(path = "/profile", method = RequestMethod.GET)
    public String getProfilePage() {
        return "/site/profile";
    }

    @LoginRequired
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String upload(MultipartFile headerImage, Model model) {
        if (headerImage == null) {
            model.addAttribute("error", "还没有选择图片");
            return "/site/setting";
        }
        String originalFilename = headerImage.getOriginalFilename();
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        if (StringUtils.isBlank(substring)) {
            model.addAttribute("error", "图片格式不正确");
            return "/site/setting";
        }
        String filename = CommunityUtils.generateUUID() + substring;
        File file = new File(uploadPath + "/" + filename);
        try {
            //存储文件
            headerImage.transferTo(file);
        } catch (IOException e) {
            logger.error("上传文件失败：" + e.getMessage());
            throw new RuntimeException("上传文件失败，服务器发生异常！" + e);
        }
        //修改数据库中user的头像属性
        //头像属性格式：http://www.localhost:8081/community/user/header/*.png
        User user = hostHolder.getUser();
        userService.updateHeader(user.getId(), domain + contextPath + "/user/header/" + filename);
        return "redirect:/index";
    }

    @RequestMapping(path = "/header/{filename}", method = RequestMethod.GET)
    public void getHeader(@PathVariable("filename") String filename, HttpServletResponse response) {
        //后缀名
        String sub = filename.substring(filename.lastIndexOf("."));
        //设置响应格式
        response.setContentType("image/" + sub);
        //图片存放的路径
        String path = uploadPath + "/" + filename;
        //响应图片
        try (
                //获取response响应流
                ServletOutputStream os = response.getOutputStream();
                //先读后写，将图片放进流里
                FileInputStream fis = new FileInputStream(path);
        ) {
            //创建缓冲区
            byte[] bytes = new byte[1024];
            int len = 0;
            //开始读取图片
            while ((len = fis.read(bytes)) != -1) {
                //图片写入response
                os.write(bytes, 0, len);
            }
        } catch (IOException e) {
            logger.error("读取头像失败：" + e.getMessage());
        }
    }

    @RequestMapping(path = "/updatepassword", method = RequestMethod.POST)
    public String updatePassword(String oldpassword, String newpassword, String repwd, Model model){
        if (StringUtils.isBlank(oldpassword)){
            model.addAttribute("olderror", "原密码不能为空");
            return "/site/setting";
        }
        if (StringUtils.isBlank(newpassword)){
            model.addAttribute("newerror", "新密码不能为空");
            return "/site/setting";
        }
        if (!newpassword.equals(repwd)){
            model.addAttribute("reerror", "两次输入的密码不一致!");
            return "/site/setting";
        }

        //获取登录的账号信息
        User user = hostHolder.getUser();
        //判断原始密码是否正确
        if (!user.getPassword().equals(CommunityUtils.md5(oldpassword + user.getSalt()))){
            model.addAttribute("olderror", "原密码不正确");
            return "/site/setting";
        }
        if (oldpassword.equals(newpassword)){
            model.addAttribute("newerror", "新密码不能和原始密码相同");
            return "/site/setting";
        }
        //修改密码
        //新密码
        String pwd = CommunityUtils.md5(newpassword + user.getSalt());
        userService.updatePassword(user.getId(), pwd);
        return "redirect:/index";
    }
}
