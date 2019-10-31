package com.fh.shop.api.util;

public class KeyUtil {

    public static String buildMember(Long id, String memberName, String uuid) {
        return "member:" + id + ":" + memberName + ":" + uuid;
    }

    public static String buildCartLtem(Long id) {
        return "user:" + id;
    }

    public static String buildPayLog(Long id) {
        return "payLog:" + id;
    }
}
