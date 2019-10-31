package com.fh.shop.admin.po.brand;

import com.fh.shop.admin.common.Page;

import java.io.Serializable;

public class Brand extends Page implements Serializable {
    private Integer id;

    private String brandName;

    private String brandImg;
    //自定义排序
    private Integer sort;
    //热销状态  1热销  2不热销
    private Integer sellState;



    public Integer getSellState() {
        return sellState;
    }

    public void setSellState(Integer sellState) {
        this.sellState = sellState;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandImg() {
        return brandImg;
    }

    public void setBrandImg(String brandImg) {
        this.brandImg = brandImg;
    }
}
