package com.fh.shop.api.area.biz;

import com.fh.shop.api.area.po.Area;

import java.util.List;

public interface IAreaService {
    List<Area> buildAreaSelect(Area area);
}
