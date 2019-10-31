package com.fh.shop.api.order.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.order.po.Order;

public interface IOrderService {
    ServerResponse addOrder(Order order, Long[] productIds, MemberVo memberVo);
}
