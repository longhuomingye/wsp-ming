package com.fh.shop.admin.po.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fh.shop.admin.po.brand.Brand;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Product implements Serializable {
    private Long id;
    private String productName;//商品名称
    private BigDecimal price;//价格
    //商品主图
    private String mainImagePath;
    //生产日期
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date producedDate;
    //库存
    private Long stock;
    //热销状态  1热销  2不热销
    private Integer sellState;
    //   上架/下架  1  2
    private Integer status;
    //品牌
    private Brand brand = new Brand();
    //品牌id
    private Integer brandId;
    //一级
    private Integer cate1;
    //二级
    private Integer cate2;
    //三级
    private Integer cate3;
    //分类展示数据
    private String categoryNames;

    public String getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(String categoryNames) {
        this.categoryNames = categoryNames;
    }

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

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Long getStock() {
        return stock;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }

    public Integer getSellState() {
        return sellState;
    }

    public void setSellState(Integer sellState) {
        this.sellState = sellState;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getMainImagePath() {
        return mainImagePath;
    }

    public void setMainImagePath(String mainImagePath) {
        this.mainImagePath = mainImagePath;
    }

    public Date getProducedDate() {
        return producedDate;
    }

    public void setProducedDate(Date producedDate) {
        this.producedDate = producedDate;
    }
}
