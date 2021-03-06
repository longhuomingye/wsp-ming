package com.fh.shop.api.weixin;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class IdUtil {

    public static String createId() {
        DateTimeFormatter yyyyMMddHHmm = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        String format = LocalDateTime.now().format(yyyyMMddHHmm);
        format = format + IdWorker.getId();
        return format;
    }
}
