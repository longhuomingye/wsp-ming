package com.fh.shop.api.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static final String Y_M_D = "yyyy-MM-dd";
    public static final String FULL_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    /**
     * 讲日期转换成字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String date2str(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sim = new SimpleDateFormat(pattern);
        String str = sim.format(date);
        return str;
    }

    /**
     * 讲字符串转为日期
     *
     * @param str
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static Date str2date(String str, String pattern) throws ParseException {
        if (str == null) {
            return null;
        }
        SimpleDateFormat sim = new SimpleDateFormat(pattern);
        Date parse = sim.parse(str);
        return parse;
    }
}