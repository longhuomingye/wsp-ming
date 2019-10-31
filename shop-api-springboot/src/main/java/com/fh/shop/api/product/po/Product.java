package com.fh.shop.api.product.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Product implements Serializable {
    private Long id;
    private String productName;//商品名称
    private BigDecimal price;//价格
    //商品主图
    private String mainImagePath;
    //生产日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date producedDate;
    //库存
    private Long stock;
    //热销状态  1热销  2不热销
    private Integer sellState;
    //   上架/下架  1  2
    private Integer status;

    @TableField(exist = false)
    private Integer number;

}
