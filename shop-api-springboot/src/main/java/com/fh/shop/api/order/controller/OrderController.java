package com.fh.shop.api.order.controller;

import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.Idempotent;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.order.biz.IOrderService;
import com.fh.shop.api.order.po.Order;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Resource(name = "orderService")
    private IOrderService orderService;

    @PostMapping
    @Check
    @Idempotent
    public ServerResponse addOrder(Order order, Long[] productIds, MemberVo memberVo) {
        return orderService.addOrder(order, productIds, memberVo);
    }


}
