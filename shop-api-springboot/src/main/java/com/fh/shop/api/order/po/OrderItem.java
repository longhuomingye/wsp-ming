package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单详细表
 */
@Data
@TableName("t_orderitem")
public class OrderItem implements Serializable {
    @TableId(type = IdType.INPUT)
    private String orberId;//订单id
    private String privateName;//商品名称
    private String img;//图片路径
    private BigDecimal price;//商品单价
    private Long subTotalCount;//商品个数
    private String subTotalPrice;//当前商品总价
    private Long productId;//商品id
    private Long memberId;//冗余字段   用户id
}
