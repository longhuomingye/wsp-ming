package com.fh.shop.api.order.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable {
    @TableId(type = IdType.INPUT)
    private String oid; //订单编号
    private Date createTime; //创建订单时间
    private BigDecimal totalPrice; //总计
    private Long totalCount;//总个数
    private int state; //状态 1 支付  2未支付  3  已发货 4 确认订单  5    已完成评价
    private String address; //收货人地址
    private String name; //收货人姓名
    private String telephone; //收货人电话
    private Long memberId;//当前下单的会员id
    private Integer paymentType;//支付方式  1 微信支付  2  货到付款
    private Date endTime;//交易完成时间
    private Date consignTime;//发货时间
    private Date paymentTime;//付款时间  支付时间
    private Date beginTime;  //交易关闭时间
    private Date finTime;//订单完成评价的时间
    private String zipCode;// 邮编
    private Integer postage;// 邮费
    private String bill;//发票
    private Integer status;  //订单的状态描述  10 未支付
}
