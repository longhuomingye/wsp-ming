package com.fh.shop.api.address.po;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    private Long id;
    private String cneeName;//收货人名称
    private String address;//地址
    private String phone;//联系电话
    private String email;//邮箱
    private String alias;//地址别名  1  家里  2  公司  3父母家
    private Long memberId;//会员id
    private Integer site;//会员id
}
