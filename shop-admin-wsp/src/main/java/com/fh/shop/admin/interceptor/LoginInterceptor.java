package com.fh.shop.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.util.DistributedSession;
import com.fh.shop.admin.util.KeyUtil;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.util.SystemConstant;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("这里是拦截器");
        //User user = (User) request.getSession().getAttribute(SystemConstant.USER);
        //获取当前登录的用户
        String sessionId = DistributedSession.getSessionId(request, response);
        String userJson = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
        User user = JSONObject.parseObject(userJson, User.class);
        //获取当前请求的路径
        if(user!=null){
            //登录成功 在每次收到请求的时候 续命
            RedisUtil.exprie(KeyUtil.buildUserKey(sessionId),SystemConstant.USER_EXPIRE);
            RedisUtil.exprie(KeyUtil.buildUserWealth(sessionId),SystemConstant.USER_EXPIRE);
            RedisUtil.exprie(KeyUtil.buildAllWealth(sessionId),SystemConstant.USER_EXPIRE);
            return true;
        }else{
            response.sendRedirect("/");
            return false;
        }
    }
}
