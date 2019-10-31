package com.fh.shop.admin.common;

import javax.servlet.http.HttpServletRequest;

public class WebContext {

    private static ThreadLocal<HttpServletRequest> threadLocal = new ThreadLocal();
    //
    public static void setRequest(HttpServletRequest request){
        threadLocal.set(request);
    }

    public static HttpServletRequest getRequset(){
        return threadLocal.get();
    }

    public static void removeRequest(){
        threadLocal.remove();
    }
}
