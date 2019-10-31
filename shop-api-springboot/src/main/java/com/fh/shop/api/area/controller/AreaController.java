package com.fh.shop.api.area.controller;

import com.fh.shop.api.area.biz.IAreaService;
import com.fh.shop.api.area.po.Area;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/areas")
public class AreaController {

    @Resource(name = "areaService")
    private IAreaService areaService;

    @GetMapping
    public ServerResponse buildAreaSelect(Area area) {
        List<Area> list = areaService.buildAreaSelect(area);
        return ServerResponse.success(ResponseEnum.SUCCESS, list);
    }
}
