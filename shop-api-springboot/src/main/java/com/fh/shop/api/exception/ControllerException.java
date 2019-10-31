package com.fh.shop.api.exception;

import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ControllerException {


    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ServerResponse handleException(GlobalException g) {
        ResponseEnum responseEnum = g.getResponseEnum();
        return ServerResponse.error(responseEnum);
    }
}
