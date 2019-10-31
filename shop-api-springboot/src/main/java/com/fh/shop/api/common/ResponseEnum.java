package com.fh.shop.api.common;

public enum ResponseEnum {
    SUCCESS(200, "ok"),
    MEMBER_ERROR(300, "信息不能为空"),
    NODE_ERROR(400, "验证码错误"),
    PHONE_ISNULL(402, "手机号为空"),
    SMS_IS_ERROR(403, "短信发送失败"),
    SMS_NODE_ERROR(425, "验证码已失效"),
    MEMBER_IS_PHONE_ERROR(516, "当前手机号已被注册"),
    PHONE_IS_ERROR(427, "输入的手机号不符合格式"),
    MEMBER_IS_NAME_EXIST(466, "当前用户名已存在"),
    MEMBER_IS_PHONE_EXIST(487, "当前手机号已被使用"),
    MEMBER_IS_EMAIL_EXIST(498, "当前邮箱已被注册"),
    MEMBER_IS_NAME_NULL(455, "用户名为空，请输入用户名"),
    MEMBER_IS_PASSWORD_NULL(430, "密码不能为空，请输入密码"),
    MEMBER_IS_NAME_ERROR(431, "用户不存在,请注册"),
    MEMBER_IS_PASSWORD_ERROR(432, "密码不正确，请重新输入"),
    HEAD_IS_NULL(433, "头信息为空"),
    HEAD_ERROR(434, "头信息丢失"),
    SIGN_IS_ERROR(435, "签名不正确"),
    MEMBER_IS_NULL(438, "当前用户已过期，请重新登录"),
    PRODUCT_IS_NULL(439, "商品不存在"),
    PRODUCT_IS_STATUS(501, "商品已下架"),
    CART_IS_NULL(506, "购物车为空"),
    PHONE_IS_NULL(508, "手机号不能为空，请输入手机号"),
    NODE_IS_NULL(600, "验证码不能为空，请输入验证码"),
    PHONE_IS_MEMBER_NULL(601, "当前手机号还没有注册，请注册!"),
    IDEM_IS_REPET(603, "请求重复"),
    IDEM_IS_MISS(605, "token丢失"),
    IS_ORDER_NULL(607, "当前订单中的所有商品库存已经不足，请重新选购"),
    IS_ORDER_MISS(609, "当前没有订单生成");

    private int code;
    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
