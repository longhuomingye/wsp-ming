package com.fh.shop.admin.po.member;

import com.baomidou.mybatisplus.annotation.TableField;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Member implements Serializable {
    private Long id;
    private String memberName;
    private String password;
    private String realName;
    private  String phone;
    private Long area1;
    private Long area2;
    private Long area3;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date brithdy;

    private String email;

    @TableField(exist = false)
    private String areanames;

    public Date getBrithdy() {
        return brithdy;
    }

    public void setBrithdy(Date brithdy) {
        this.brithdy = brithdy;
    }

    public String getAreanames() {
        return areanames;
    }

    public void setAreanames(String areanames) {
        this.areanames = areanames;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getArea1() {
        return area1;
    }

    public void setArea1(Long area1) {
        this.area1 = area1;
    }

    public Long getArea2() {
        return area2;
    }

    public void setArea2(Long area2) {
        this.area2 = area2;
    }

    public Long getArea3() {
        return area3;
    }

    public void setArea3(Long area3) {
        this.area3 = area3;
    }
}
