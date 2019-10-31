package com.fh.shop.api.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //设置ajax支持跨域"Access-Control-Allow-Credentials"
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        System.out.println();
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST,GET,DELETE,PUT");
        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, "x-requested-with,Authorization,x-auth,content-type,token");
        //过滤options请求
        String methodType = request.getMethod();
        if (methodType.equalsIgnoreCase("options")) {
            return false;
        }
        //获取自定义注解
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //如果没有使用自定义注解  就是公共资源 放开
        if (!method.isAnnotationPresent(Check.class)) {
            return true;
        }
        String header = request.getHeader("x-auth");
        //判断是否有头信息
        if (StringUtils.isEmpty(header)) {
            throw new GlobalException(ResponseEnum.HEAD_IS_NULL);
        }
        String[] split = header.split("\\.");
        //判断头信息是否丢失
        if (split.length != 2) {
            throw new GlobalException(ResponseEnum.HEAD_ERROR);
        }
        //判断签名是否正确
        String memberBase = split[0];
        String signBase = split[1];
        byte[] decode = Base64.getDecoder().decode(signBase);
        String signJson = new String(decode);
        String sign = Md5Util.sign(memberBase, SystemConstant.SECRET);
        if (!signJson.equals(sign)) {
            throw new GlobalException(ResponseEnum.SIGN_IS_ERROR);
        }
        //解密 然后拿到用户信息
        byte[] decode1 = Base64.getDecoder().decode(memberBase);
        String memberJson = new String(decode1);
        MemberVo memberVo = JSONObject.parseObject(memberJson, MemberVo.class);
        String memberName = memberVo.getMemberName();
        String uuid = memberVo.getUuid();
        Long id = memberVo.getId();
        //判断当前用户是否过期
        String key = KeyUtil.buildMember(id, memberName, uuid);
        boolean exists = RedisUtil.exists(key);
        if (!exists) {
            throw new GlobalException(ResponseEnum.MEMBER_IS_NULL);
        }
        //续命
        RedisUtil.exprie(key, SystemConstant.MEMBER_EXPIRE);
        //存放用户名到request中
        request.setAttribute("member", memberVo);
        return true;
    }
}
