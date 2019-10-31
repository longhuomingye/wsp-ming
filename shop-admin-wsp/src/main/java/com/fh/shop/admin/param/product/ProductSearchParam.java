package com.fh.shop.admin.param.product;

import com.fh.shop.admin.common.Page;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ProductSearchParam extends Page implements Serializable {
    private String productName;//商品名称
    private String price;//价格
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date producedDate;
    //生产日期条件查询
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxTime;
    //品牌查询
    private Integer brandId;
    //分类id数组
    private Integer[] selectCategory;
    //分类 数组 长度
    private Integer categoryLength;
    //一级
    private Integer cate1;
    //二级
    private Integer cate2;
    //三级
    private Integer cate3;

    public Integer getCate1() {
        return cate1;
    }

    public void setCate1(Integer cate1) {
        this.cate1 = cate1;
    }

    public Integer getCate2() {
        return cate2;
    }

    public void setCate2(Integer cate2) {
        this.cate2 = cate2;
    }

    public Integer getCate3() {
        return cate3;
    }

    public void setCate3(Integer cate3) {
        this.cate3 = cate3;
    }

    public Integer getCategoryLength() {
        return categoryLength;
    }

    public void setCategoryLength(Integer categoryLength) {
        this.categoryLength = categoryLength;
    }

    public Integer[] getSelectCategory() {
        return selectCategory;
    }

    public void setSelectCategory(Integer[] selectCategory) {
        this.selectCategory = selectCategory;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Date getMinTime() {
        return minTime;
    }

    public void setMinTime(Date minTime) {
        this.minTime = minTime;
    }

    public Date getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(Date maxTime) {
        this.maxTime = maxTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Date getProducedDate() {
        return producedDate;
    }

    public void setProducedDate(Date producedDate) {
        this.producedDate = producedDate;
    }
}
