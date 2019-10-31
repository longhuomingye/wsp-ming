package com.fh.shop.admin.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class WebContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //把当前访问的request放入thredFilter中
        WebContext.setRequest((HttpServletRequest) servletRequest);
        try{
            //继续执行访问的请求  相当于 拦截器里面 return true
            filterChain.doFilter(servletRequest,servletResponse);
        }finally {
            //使用完毕 删除 释放资源
            WebContext.removeRequest();
        }
    }

    @Override
    public void destroy() {

    }
}
