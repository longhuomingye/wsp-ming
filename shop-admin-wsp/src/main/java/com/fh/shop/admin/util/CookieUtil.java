package com.fh.shop.admin.util;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    /**
     *写入浏览器会话cookie中
     * @param name
     * @param value
     * @param domain
     * @param response
     */
    public static void write(String name, String value, String domain, HttpServletResponse response){
        Cookie cookie = new Cookie(name,value);
        //设置域名 否则获取不到 cookie中的值
        cookie.setDomain(domain);
        //代表项目根目录下
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 读取对应的cookie
     * @param name
     * @param request
     * @return
     */
    public static String read(String name,HttpServletRequest request){
        //获取所有的cookie
        Cookie[] cookies = request.getCookies();
        //判断是否为空 如果为空 说明是第一次访问 返回空字符串
        if(null == cookies){
            return "";
        }
        //不为空  循环 找出对应的信息  比如 自定义的sessionId
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals(name)){
                return cookie.getValue();
            }
        }
        //不存在 返回空字符串
        return "";
    }
}
