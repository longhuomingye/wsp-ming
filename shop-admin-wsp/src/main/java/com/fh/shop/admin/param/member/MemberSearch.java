package com.fh.shop.admin.param.member;

import com.fh.shop.admin.common.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class MemberSearch extends Page implements Serializable  {
    private String memberName;
    private String phone;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxDate;

    private Long area1;
    private Long area2;
    private Long area3;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
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

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
}
