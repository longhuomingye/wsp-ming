package com.fh.shop.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.admin.po.wealth.Wealth;
import com.fh.shop.admin.util.DistributedSession;
import com.fh.shop.admin.util.KeyUtil;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.util.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class permissionInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //当前用户对应的资源
        //List<WealthVo> mamuUserList = (List<WealthVo>) request.getSession().getAttribute(SystemConstant.MANU_USER);
        String sessionId = DistributedSession.getSessionId(request, response);
        String mamuUserListJson = RedisUtil.get(KeyUtil.buildUserWealth(sessionId));
        List<Wealth> mamuUserList = JSONObject.parseArray(mamuUserListJson, Wealth.class);
        //资源表的所有资源
        //List<Wealth> mamuList = (List<Wealth>) request.getSession().getAttribute(SystemConstant.MANULIST);
        String mamuListJson = RedisUtil.get(KeyUtil.buildAllWealth(sessionId));
        List<Wealth> mamuList = JSONObject.parseArray(mamuListJson, Wealth.class);
        //获取当前请求的请求名称
        String requestURI = request.getRequestURI();
        //放开公共资源
        boolean f = true;
        for (Wealth wealth : mamuList) {
            if(StringUtils.isNotEmpty(wealth.getUrl())){
                //包含说明是需要拦截的资源
                if(requestURI.contains(wealth.getUrl())){
                    f = false;
                    break;
                }
            }
        }
        //如果 进不了 if 说明是公告资源 放开 继续执行
        if(f){
            return f;
        }
        boolean flag = false;
        //判断当前用户是否拥有操作此资源的权限
        for (Wealth wealthVo : mamuUserList) {
            if(StringUtils.isNotEmpty(wealthVo.getUrl())){
                if(requestURI.contains(wealthVo.getUrl())){
                    flag =  true;
                    break;
                }
            }
        }
        //如果进了if说明有权限  继续执行
        if(flag){
            return flag;
        }else{
            //获取ajax请求
            String header = request.getHeader("X-Requested-With");
            //判断当前请求是否为ajax请求
            if(StringUtils.isNotEmpty(header) && header.equals("XMLHttpRequest")){
                Map map = new HashMap();
                map.put("code","1005");
                map.put("msg","没有访问权限");
                String s = JSONObject.toJSONString(map);
                System.out.println(s);
                //解决中文乱码  设置编码为utf-8
                response.setContentType("application/json;charset=UTF-8");
                //响应数据给前台
                PrintWriter writer=response.getWriter();
                writer.write(s);
                //刷新
                writer.flush();
                writer.close();
                return false;
            }else{
                //如果当前请求是普通请求 跳转到错误界面
                response.sendRedirect(SystemConstant.ERRORJSP);
                return false;
            }
        }
    }
}
