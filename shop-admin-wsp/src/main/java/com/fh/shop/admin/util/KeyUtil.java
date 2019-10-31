package com.fh.shop.admin.util;

public class KeyUtil {
    /**
     * 验证码 key
     * @param sessionId
     * @return
     */
    public static String buildCodeKey(String sessionId){
        return "code:"+sessionId;
    }

    /**
     * 用户key
     * @param sessionId
     * @return
     */
    public static String buildUserKey(String sessionId){
        return "user:"+sessionId;
    }

    /**
     * 用户对应的资源 Key
     * @param sessionId
     * @return
     */
    public static String buildUserWealth(String sessionId){
        return "UserWealth:"+sessionId;
    }

    /**
     * 所有的资源key
     * @param sessionId
     * @return
     */
    public static String buildAllWealth(String sessionId){
        return "AllWealth:"+sessionId;
    }
}
