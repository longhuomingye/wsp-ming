package com.fh.shop.admin.common;

public enum ResponseEnum {
    USERNAME_PASSWORD_IS_ERROR(1000,"用户名、密码或验证码不能为空"),
    USERNAME_ERROR(1001,"用户名不存在"),
    PASSWORD_ERROR(1002,"密码不正确"),
    USER_IS_NULL(2006,"用户不存在"),
    USER_ERROR(1003,"当前用户已被锁定,请明天再试"),
    USER_PASSWORD_IS_NULL(1004,"原密码或新密码或确认密码不能为空"),
    NEWPASSWORD_CONPASSWORD_IS_ERROR(2001,"新密码和确认密码不一致"),
    OLDPASSWORD_IS_ERROR(2002,"原密码不正确,请重新输入"),
    FORGET_PASSWORD(2003,"重置成功,新密码已发送至邮箱请查看"),
    FORGET_PASSWORD_ERROR(2004,"邮箱不存在，请输入正确的邮箱"),
    SUCCESS(200,"ok"),
    CODE_ERROR(630,"验证码不正确");

    private int code;
    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }



    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
