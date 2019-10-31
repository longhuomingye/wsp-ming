package com.fh.shop.api.Idempotent.controller;

import com.fh.shop.api.Idempotent.biz.IdemService;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/idems")
public class IdemController {

    @Resource(name = "idemService")
    private IdemService idemService;

    @GetMapping
    @Check
    public ServerResponse createIdem() {
        return idemService.createIdem();
    }
}
