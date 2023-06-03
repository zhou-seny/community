package com.zhou.community.config;

import com.zhou.community.controller.interceptor.LoginRequiredInterceptor;
import com.zhou.community.controller.interceptor.LoginTicketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginTicketInterceptor loginTicketInterceptor;

    @Autowired
    private LoginRequiredInterceptor loginRequiredInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截所有请求，将静态资源过滤掉，如果cookie中有ticket， 并且还没过期，直接登录，显示登录内容（用户名）
        registry.addInterceptor(loginTicketInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");
//                .addPathPatterns("/login", "/register");
        //拦截所有请求，将静态资源过滤掉，（本来不需要配置，因为LoginRequiredInterceptor那边已经拦截了指定注解的方法）
        registry.addInterceptor(loginRequiredInterceptor)
                .excludePathPatterns("/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.jpeg");
    }
}
