package com.fh.shop.admin.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class DistributedSession {

    public static String getSessionId(HttpServletRequest req, HttpServletResponse resp){
        //从cookie中获取当前sessionid
        String sessionId = CookieUtil.read(SystemConstant.SESSIONId_NAME, req);
        //判断是否存在 不为空 返回
        if(StringUtils.isNotEmpty(sessionId)){
            return sessionId;
        }
        //为空 创建
        sessionId = UUID.randomUUID().toString();
        CookieUtil.write(SystemConstant.SESSIONId_NAME,sessionId,SystemConstant.DOMAIN,resp);
        return sessionId;
    }
}
