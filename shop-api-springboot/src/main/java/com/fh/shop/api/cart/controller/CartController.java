package com.fh.shop.api.cart.controller;

import com.fh.shop.api.cart.biz.ICartService;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.product.po.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Resource(name = "cartService")
    private ICartService cartService;
    @Autowired
    private HttpServletRequest request;

    /**
     * 购物车
     *
     * @param productId
     * @param count
     * @return
     */
    @PostMapping
    @Check
    public ServerResponse addCart(Long productId, Long count, MemberVo member) {
        return cartService.addCart(productId, count, member.getId());
    }

    /**
     * 查询购物车
     *
     * @return
     */
    @GetMapping
    @Check
    public ServerResponse findCart(MemberVo member) {
        return cartService.findCart(member);
    }

    /**
     * 删除商品
     *
     * @param prodcutId
     * @return
     */
    @DeleteMapping(value = "/{prodcutId}")
    @Check
    public ServerResponse delCartIter(@PathVariable Integer[] prodcutId, MemberVo member) {
        return cartService.delCartIter(prodcutId, member.getId());
    }
}
