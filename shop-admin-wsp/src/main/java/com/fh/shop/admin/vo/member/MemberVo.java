package com.fh.shop.admin.vo.member;

public class MemberVo {
    private Long id;
    private String memberName;
    private String realName;
    private  String phone;

    private String email;

    private String areanames;
    private String brithdy;

    public String getBrithdy() {
        return brithdy;
    }

    public void setBrithdy(String brithdy) {
        this.brithdy = brithdy;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAreanames() {
        return areanames;
    }

    public void setAreanames(String areanames) {
        this.areanames = areanames;
    }
}
