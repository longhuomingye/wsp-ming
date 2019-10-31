package com.fh.shop.api.cart.vo;

import java.io.Serializable;

public class CartItem implements Serializable {
    private Long privateId;//商品id
    private String privateName;//商品名称
    private String img;//图片
    private String price;//价格
    private Long subTotalCount;//商品个数
    private String subTotalPrice;//小计

    public Long getPrivateId() {
        return privateId;
    }

    public void setPrivateId(Long privateId) {
        this.privateId = privateId;
    }

    public String getPrivateName() {
        return privateName;
    }

    public void setPrivateName(String privateName) {
        this.privateName = privateName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Long getSubTotalCount() {
        return subTotalCount;
    }

    public void setSubTotalCount(Long subTotalCount) {
        this.subTotalCount = subTotalCount;
    }

    public String getSubTotalPrice() {
        return subTotalPrice;
    }

    public void setSubTotalPrice(String subTotalPrice) {
        this.subTotalPrice = subTotalPrice;
    }
}
