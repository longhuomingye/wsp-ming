package com.fh.shop.api.common;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

public class Page implements Serializable {
    @TableField(exist = false)
    private Integer draw;
    @TableField(exist = false)
    private Integer start;
    @TableField(exist = false)
    private Integer length;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
