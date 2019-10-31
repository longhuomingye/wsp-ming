package com.fh.shop.admin.vo.wealth;

import java.io.Serializable;

public class WealthVo implements Serializable {
    private Integer id;
    private String name;
    private Integer pId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
