package com.fh.shop.admin.param;

import com.fh.shop.admin.common.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class UserSearchParam extends Page implements Serializable {
    private Long id;//id
    private String userName;//用户名
    private String realName;//真实姓名
    //这是年龄范围查询
    private Integer minAge;
    private Integer maxAge;

    //这是薪资范围查询
    private Double minPay;
    private Double maxPay;

    //这是入职时间范围查询
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minDemo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxDemo;
    //条件查询用的
    private Integer[] roleIds;
    //这是条件查询:角色id数组的长度
    private Integer roleIdsLengTh;
    //这是条件查询地区id数组
    private Integer[] selectArea;
    //地区一级
    private Integer area1;
    //地区二级
    private Integer area2;
    //地区三级
    private Integer area3;

    public Integer[] getSelectArea() {
        return selectArea;
    }

    public void setSelectArea(Integer[] selectArea) {
        this.selectArea = selectArea;
    }

    public Integer getArea1() {
        return area1;
    }

    public void setArea1(Integer area1) {
        this.area1 = area1;
    }

    public Integer getArea2() {
        return area2;
    }

    public void setArea2(Integer area2) {
        this.area2 = area2;
    }

    public Integer getArea3() {
        return area3;
    }

    public void setArea3(Integer area3) {
        this.area3 = area3;
    }

    public Integer getRoleIdsLengTh() {
        return roleIdsLengTh;
    }

    public void setRoleIdsLengTh(Integer roleIdsLengTh) {
        this.roleIdsLengTh = roleIdsLengTh;
    }

    public Integer[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Integer[] roleIds) {
        this.roleIds = roleIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(Integer maxAge) {
        this.maxAge = maxAge;
    }

    public Double getMinPay() {
        return minPay;
    }

    public void setMinPay(Double minPay) {
        this.minPay = minPay;
    }

    public Double getMaxPay() {
        return maxPay;
    }

    public void setMaxPay(Double maxPay) {
        this.maxPay = maxPay;
    }

    public Date getMinDemo() {
        return minDemo;
    }

    public void setMinDemo(Date minDemo) {
        this.minDemo = minDemo;
    }

    public Date getMaxDemo() {
        return maxDemo;
    }

    public void setMaxDemo(Date maxDemo) {
        this.maxDemo = maxDemo;
    }
}
