package com.fh.shop.api.Interceptor;

import com.fh.shop.api.common.Idempotent;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.exception.GlobalException;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class IdemInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //获取方法签名
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //判断当前请求是否需要幂等性
        if (!method.isAnnotationPresent(Idempotent.class)) {
            return true;
        }
        String token = request.getHeader("token");
        //判断当前头信息是否有token
        if (StringUtils.isEmpty(token)) {
            throw new GlobalException(ResponseEnum.HEAD_IS_NULL);
        }
        //判断当前token在redis中是否存在
        boolean exists = RedisUtil.exists(token);
        if (!exists) {
            throw new GlobalException(ResponseEnum.IDEM_IS_MISS);
        }
        //判断当前token是否被删除成功
        Long del = RedisUtil.del(token);
        if (del != 1) {
            throw new GlobalException(ResponseEnum.IDEM_IS_REPET);
        }
        return true;
    }
}
