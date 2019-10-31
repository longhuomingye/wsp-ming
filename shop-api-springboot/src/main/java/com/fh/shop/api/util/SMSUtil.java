package com.fh.shop.api.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SMSUtil {

    private static String APPKEY = "f0a4ee6dfadcdbb96d989e362eedc242";
    private static String NONCE = "123456";
    private static String APPSECRET = "0606d51e9ca1";
    private static String URL = "https://api.netease.im/sms/sendcode.action";
    private static final String TEMPLATEID = "14844209";

    public static String smsSend(String phone) {
        //设置请求头的map集合
        Map<String, String> map = new HashMap<>();
        map.put("AppKey", APPKEY);
        map.put("Nonce", NONCE);
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        map.put("CurTime", curTime);
        String checkSum = CheckSumBuilder.getCheckSum(APPSECRET, NONCE, curTime);
        map.put("CheckSum", checkSum);
        //设置参数列表的map集合
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("mobile", phone);
        paramMap.put("templateid", TEMPLATEID);
        String str = HttpClientUtil.sendHttpClient(URL, map, paramMap);
        return str;
    }
}
