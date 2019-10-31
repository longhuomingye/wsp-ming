package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.cart.vo.Cart;
import com.fh.shop.api.cart.vo.CartItem;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service("cartService")
@Transactional(rollbackFor = Exception.class)
public class ICartServiceImpl implements ICartService {

    @Autowired
    private IProductMapper productMapper;

    @Override
    public ServerResponse addCart(Long productId, Long count, Long id) {
        //通过商品id查询当前商品
        Product productInfo = getProduct(productId);
        //判断商品是否存在
        if (productInfo == null) {
            return ServerResponse.error(ResponseEnum.PRODUCT_IS_NULL);
        }
        //判断商品是否上架
        if (productInfo.getStatus() == 2) {
            return ServerResponse.error(ResponseEnum.PRODUCT_IS_STATUS);
        }
        //判断当前用户是否有购物车 是否有当前商品
        String hget = RedisUtil.hget(SystemConstant.CARTNAME, KeyUtil.buildCartLtem(id));
        //没有
        if (StringUtils.isEmpty(hget)) {
            //构建购物车
            Cart cart = new Cart();
            //构建商品
            CartItem cartItem = buildCartItem(productId, count, productInfo);
            cart.getList().add(cartItem);
            //添加到购物车类中 修改数据
            updateCart(id, cart);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        }
        //如果有购物车 找出对应的商品
        Cart cart = JSONObject.parseObject(hget, Cart.class);
        List<CartItem> list = cart.getList();
        CartItem cartItem = getCartItem(productId, list);
        //如果没有对应商品  添加到购物车
        if (cartItem == null) {
            CartItem cartItem1 = buildCartItem(productId, count, productInfo);
            cart.getList().add(cartItem1);
            //更新redis中的数据
            updateCart(id, cart);
            return ServerResponse.success(ResponseEnum.SUCCESS);
        }
        //如果有 更新 小计和数量
        Long subTotalCount = count + cartItem.getSubTotalCount();
        if (subTotalCount == 0) {
            removeCartItem(list, productId);
        } else {
            cartItem.setSubTotalCount(subTotalCount);
            BigDecimal mul = BigDecimalUtil.mul(productInfo.getPrice().toString(), subTotalCount.toString());
            cartItem.setSubTotalPrice(mul.toString());
        }
        //更新redis中的数据
        updateCart(id, cart);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @Transactional(readOnly = true)
    @Override
    public ServerResponse findCart(MemberVo member) {
        String hget = RedisUtil.hget(SystemConstant.CARTNAME, KeyUtil.buildCartLtem(member.getId()));
        Cart cart = JSONObject.parseObject(hget, Cart.class);
        return ServerResponse.success(ResponseEnum.SUCCESS, cart);
    }

    /**
     * 删除商品
     *
     * @param prodcutId
     * @param id
     * @return
     */
    @Override
    public ServerResponse delCartIter(Integer[] prodcutId, Long id) {
        //查询当前用户的购物车
        String hget = RedisUtil.hget(SystemConstant.CARTNAME, KeyUtil.buildCartLtem(id));
        //判断当前购物车是否存在
        if (StringUtils.isEmpty(hget)) {
            return ServerResponse.error(ResponseEnum.CART_IS_NULL);
        }
        //存在删除商品
        Cart cart = deleteCartIterById(prodcutId, hget);
        //更新
        updateCart(id, cart);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    Cart deleteCartIterById(Integer[] prodcutId, String hget) {
        Cart cart = JSONObject.parseObject(hget, Cart.class);
        List<CartItem> cartList = cart.getList();
        for (Integer aLong : prodcutId) {
            removeCartItem(cartList, aLong.longValue());
        }
        return cart;
    }

    void removeCartItem(List<CartItem> cartList, Long aLong) {
        for (int i = 0; i < cartList.size(); i++) {
            CartItem cartItem = cartList.get(i);
            if (cartItem.getPrivateId().equals(aLong)) {
                cartList.remove(i);
                break;
            }
        }
    }

    void updateCart(Long id, Cart cart) {
        //如果当前购物车没有商品 则 删除购物车
        List<CartItem> list = cart.getList();
        if (list.size() == 0) {
            RedisUtil.hdel(SystemConstant.CARTNAME, KeyUtil.buildCartLtem(id));
        } else {
            BigDecimal totalPrice = new BigDecimal(0);
            Long totalCount = 0l;
            for (CartItem item : list) {
                totalPrice = BigDecimalUtil.add(totalPrice.toString(), item.getSubTotalPrice());
                totalCount += item.getSubTotalCount();
            }
            cart.setTotalCount(totalCount);
            cart.setTotalPrice(totalPrice.toString());
            String jsonString = JSONObject.toJSONString(cart);
            RedisUtil.hset(SystemConstant.CARTNAME, KeyUtil.buildCartLtem(id), jsonString);
        }
    }

    CartItem buildCartItem(Long productId, Long count, Product productInfo) {
        CartItem cartItem = new CartItem();
        cartItem.setImg(productInfo.getMainImagePath());
        cartItem.setPrivateId(productId);
        String price = productInfo.getPrice().toString();
        cartItem.setPrice(price);
        cartItem.setPrivateName(productInfo.getProductName());
        cartItem.setSubTotalCount(count);
        //计算小计
        String mul = BigDecimalUtil.mul(price, count.toString()).toString();
        cartItem.setSubTotalPrice(mul);
        return cartItem;
    }

    CartItem getCartItem(Long productId, List<CartItem> list) {
        CartItem cartItem = null;
        for (CartItem cartItem1s : list) {
            if (cartItem1s.getPrivateId().equals(productId)) {
                cartItem = cartItem1s;
                break;
            }
        }
        return cartItem;
    }


    public ServerResponse test(Long productId, Long count, Long id) {
        //通过商品id查询当前商品
        Product productInfo = getProduct(productId);
        //判断商品是否存在
        if (productInfo == null) {
            return ServerResponse.error(ResponseEnum.PRODUCT_IS_NULL);
        }
        //判断商品是否上架
        if (productInfo.getStatus() == 2) {
            return ServerResponse.error(ResponseEnum.PRODUCT_IS_STATUS);
        }
        //判断当前用户是否有购物车
        String hget = RedisUtil.hget(SystemConstant.CARTNAME, KeyUtil.buildCartLtem(id));
        Cart cart = null;
        if (StringUtils.isEmpty(hget)) {
            cart = new Cart();
        } else {
            //如果有 拿到原来的购物车
            cart = JSONObject.parseObject(hget, Cart.class);
        }
        //判断购物车中 是否有当前这个商品
        CartItem cartItem = getCartItem(productId, cart.getList());
        if (cartItem == null) {
            cartItem = buildCartItem(productId, count, productInfo);
            cart.getList().add(cartItem);
        } else {
            //如果有 更新 小计和数量
            Long subTotalCount = count + cartItem.getSubTotalCount();
            cartItem.setSubTotalCount(subTotalCount);
            BigDecimal mul = BigDecimalUtil.mul(productInfo.getPrice().toString(), subTotalCount.toString());
            cartItem.setSubTotalPrice(mul.toString());
        }
        //更新数据
        updateCart(id, cart);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    Product getProduct(Long productId) {
        QueryWrapper queryWrapper = new QueryWrapper<Product>();
        queryWrapper.eq("id", productId);
        return productMapper.selectOne(queryWrapper);
    }
}
