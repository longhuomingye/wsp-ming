package com.fh.shop.admin.po.wealth;

import java.io.Serializable;

/**
 * 这是菜单表单
 */
public class Wealth implements Serializable {

    private Integer id;//id
    private String memuName;//菜单名称
    private Integer fatherId;//父id
    private Integer memuType;//菜单类型
    private String url;//跳转路径

    public Integer getMemuType() {
        return memuType;
    }

    public void setMemuType(Integer memuType) {
        this.memuType = memuType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemuName() {
        return memuName;
    }

    public void setMemuName(String memuName) {
        this.memuName = memuName;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }
}
