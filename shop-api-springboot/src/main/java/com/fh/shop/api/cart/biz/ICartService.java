package com.fh.shop.api.cart.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.product.po.Product;

public interface ICartService {
    ServerResponse addCart(Long productId, Long count, Long id);

    ServerResponse findCart(MemberVo member);

    ServerResponse delCartIter(Integer[] prodcutId, Long id);
}
