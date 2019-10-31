package com.fh.shop.api.sms.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.biz.IMemberService;
import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.sms.result.SMSResult;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SMSUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;

@RestController
@RequestMapping("/sms")
public class SmsController {

    @Autowired
    private IMemberMapper memberMapper;

    @GetMapping("/{phone}/{key}")
    public ServerResponse phone(@PathVariable String phone, @PathVariable String key) throws IOException {
        //验证手机号是否为空
        if (StringUtils.isEmpty(phone)) {
            return ServerResponse.error(ResponseEnum.PHONE_ISNULL);
        }
        //验证手机号格式是否符合正则表达式  符合规则
        String pattern = "^1(3|5|7|8)\\d{9}";
        //String pattern = "^[1][3,5,7,8]\\d{9}"
        if (!phone.matches(pattern)) {
            return ServerResponse.error(ResponseEnum.PHONE_IS_ERROR);
        }
        //验证是否是登陆请求  如果不为空 说明 是登陆请求
        if (StringUtils.isNotEmpty(key)) {
            //查询数据库 查看当前手机号 是否注册
            QueryWrapper queryWrapper = new QueryWrapper<Member>();
            queryWrapper.eq("phone", phone);
            Member member = memberMapper.selectOne(queryWrapper);
            if (member == null) {
                return ServerResponse.error(ResponseEnum.PHONE_IS_MEMBER_NULL);
            }
        }
        String node = SMSUtil.smsSend(phone);
        SMSResult smsResult = JSONObject.parseObject(node, SMSResult.class);
        Integer code = smsResult.getCode();
        if (code != 200) {
            return ServerResponse.error(ResponseEnum.SMS_IS_ERROR);
        }
        RedisUtil.setEx("node:" + phone, smsResult.getObj(), 60 * 3);
        return ServerResponse.success(ResponseEnum.SUCCESS, "已发送，验证码有效时间:三分钟");
    }
}
