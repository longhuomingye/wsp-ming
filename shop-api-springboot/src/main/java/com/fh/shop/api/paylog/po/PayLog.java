package com.fh.shop.api.paylog.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 支付日志表
 */
@Data
@TableName("t_payLog")
public class PayLog implements Serializable {
    @TableId(type = IdType.INPUT, value = "out_trade")
    private String outTrade;//商家标识
    private Long memberId;//会员id
    private String orderId;//订单编号
    private String tarnsActionId;//微信支付订单号
    private Date createTime;//创建支付日志时间
    private Date payTime;//支付时间
    private BigDecimal payMoney;//金额
    private Integer payType;//支付平台  1 微信  2 支付宝
    private Integer payStatus;//状态  （1 未支付  2 支付  3 退款）
}
