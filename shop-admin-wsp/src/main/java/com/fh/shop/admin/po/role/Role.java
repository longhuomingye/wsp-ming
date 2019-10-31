package com.fh.shop.admin.po.role;

import com.fh.shop.admin.common.Page;

import java.io.Serializable;
import java.util.List;

public class Role extends Page implements Serializable {
    private Long id;
    private String roleName;
    //角色资源中间表的id数组
    private Integer[] wealthIds;
    //角色资源中间表的name字符串
    private String wealthNames;

    public String getWealthNames() {
        return wealthNames;
    }

    public void setWealthNames(String wealthNames) {
        this.wealthNames = wealthNames;
    }

    public Integer[] getWealthIds() {
        return wealthIds;
    }

    public void setWealthIds(Integer[] wealthIds) {
        this.wealthIds = wealthIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
