package com.zhou.community.controller.interceptor;

import com.zhou.community.annotation.LoginRequired;
import com.zhou.community.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {
    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            //将handler转型， 便于取值
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            //获取方法
            Method method = handlerMethod.getMethod();
            //获取该方法头上的指定类型的注解
            LoginRequired annotation = method.getAnnotation(LoginRequired.class);
            //如果指定类型的注解不为null（表示需要登录），并且还没有登录，将其转到login页面
            if (annotation != null && hostHolder.getUser() == null) {
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }
}
