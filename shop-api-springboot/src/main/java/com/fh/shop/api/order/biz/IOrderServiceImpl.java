package com.fh.shop.api.order.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.cart.vo.Cart;
import com.fh.shop.api.cart.vo.CartItem;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.member.vo.MemberVo;
import com.fh.shop.api.order.mapper.IOrderIterMapper;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.po.Order;
import com.fh.shop.api.order.po.OrderItem;
import com.fh.shop.api.paylog.mapper.IPayLogMapper;
import com.fh.shop.api.paylog.po.PayLog;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.SystemConstant;
import com.fh.shop.api.weixin.IdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service("orderService")
@Transactional(rollbackFor = Exception.class)
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderMapper orderMapper;

    @Autowired
    private IOrderIterMapper orderIterMapper;

    @Autowired
    private IProductMapper productMapper;

    @Autowired
    private IPayLogMapper payLogMapper;

    @Override
    public ServerResponse addOrder(Order order, Long[] productIds, MemberVo memberVo) {
        //订单id
        String timeId = IdUtil.createId();
        //构建订单详细信息
        String hget = RedisUtil.hget(SystemConstant.CARTNAME, KeyUtil.buildCartLtem(memberVo.getId()));
        //获取当前用户的购物车
        Cart cart = JSONObject.parseObject(hget, Cart.class);
        //获取当前用户购物车中提交需要结算的商品 保存数据库
        List<CartItem> cartList = cart.getList();
        //存放 库存不足的商品
        List<OrderItem> orderItems = new ArrayList<>();
        //用于更新redis数据库购物车数据的集合 应用场景:  有的商品库存不够  不能提交成订单 有的商品够 还是生成了订单 所以不能删掉购物车
        List<CartItem> cartItems = new ArrayList<>();
        for (int j = cartList.size() - 1; j >= 0; j--) {
            CartItem cartItem = cartList.get(j);
            OrderItem orderItem = new OrderItem();
            orderItem.setPrivateName(cartItem.getPrivateName());
            orderItem.setImg(cartItem.getImg());
            orderItem.setOrberId(timeId);
            orderItem.setPrice(new BigDecimal(cartItem.getPrice()));
            orderItem.setProductId(cartList.get(j).getPrivateId());
            orderItem.setSubTotalCount(cartItem.getSubTotalCount());
            orderItem.setSubTotalPrice(cartItem.getSubTotalPrice());
            orderItem.setMemberId(memberVo.getId());
            //根据id查询当前商品的库存
            Product product = productMapper.selectById(cartList.get(j).getPrivateId());
            /*//把当前商品的库存保存到redis中
            RedisUtil.set("product:"+product.getId(),product.getStock().toString());
            //取出当前商品的库存
            String number = RedisUtil.get("product:" + product.getId());
            Integer integer = Integer.valueOf(number);*/
            //判断需要购买的商品的数量是否够
            if (orderItem.getSubTotalCount() > product.getStock()) {
                //如果不够 添加到集合中 在前台展示
                orderItems.add(orderItem);
                //同时从当前商品集合中移除该商品 用于重新计算商品总计已经总个数
                cartList.remove(j);
                //添加到 更新redis购物车所用的集合中
                cartItems.add(cartItem);
                System.out.println();
            } else {
                //获取当前商品的数量
                Long subTotalCount = orderItem.getSubTotalCount();
                //判断高并发情况下 是否修改数据
                Long flag = productMapper.updateStockById(subTotalCount, product.getId());
                //没有修改 说明库存不够 移除商品
                if (flag == 0) {
                    //如果不够 添加到集合中 在前台展示
                    orderItems.add(orderItem);
                    //同时从当前商品集合中移除该商品 用于重新计算商品总计已经总个数
                    cartList.remove(j);
                    //添加到 更新redis购物车所用的集合中
                    cartItems.add(cartItem);
                } else {
                    long stock = product.getStock() - orderItem.getSubTotalCount();
                    orderIterMapper.insert(orderItem);
                    product.setStock(stock);
                    productMapper.updateById(product);
                }
            }
            /*//更新商品库存
            Long increment = RedisUtil.increment("product:" + product.getId(), -orderItem.getSubTotalCount());
            if(increment>=0){
                //插入数据到订单详细表
                long stock = product.getStock() - orderItem.getSubTotalCount();
                orderIterMapper.insert(orderItem);
                product.setStock(stock);
                productMapper.updateById(product);
            }else{
                //恢复刚刚减去的库存
                RedisUtil.increment("product:" + product.getId(),orderItem.getSubTotalCount());
            }*/
        }
        //判断订单中是否有商品  如果为0  不生成订单
        if (cartList.size() == 0) {
            return ServerResponse.error(ResponseEnum.IS_ORDER_NULL, orderItems);
        }
        //构建订单
        order.setOid(timeId);
        //创建订单时间
        order.setCreateTime(new Date());
        //订单状态 1 支付  2 未支付
        order.setState(2);
        //当前下单的会员id
        order.setMemberId(memberVo.getId());
        //状态
        order.setStatus(10);
        //总价格
        order.setTotalPrice(new BigDecimal(cart.getTotalPrice()));
        //总个数
        order.setTotalCount(cart.getTotalCount());
        //否则 生成订单 保存数据库
        orderMapper.insert(order);
        //同时  生成 支付日志表 插入数据库
        PayLog payLog = new PayLog();
        //生成支付日志表id
        String id = IdUtil.createId();
        payLog.setOutTrade(id);
        payLog.setMemberId(memberVo.getId());
        payLog.setOrderId(timeId);
        payLog.setCreateTime(new Date());
        payLog.setPayMoney(order.getTotalPrice());
        payLog.setPayType(1);//微信
        payLog.setPayStatus(1);//未支付
        //保存数据库
        payLogMapper.insert(payLog);
        String jsonString = JSONObject.toJSONString(payLog);
        //保存到redis缓存服务器
        String key = KeyUtil.buildPayLog(memberVo.getId());
        RedisUtil.set(key, jsonString);
        //更新数据  判断当前库存不足的商品集合 数量是否大于0
        if (orderItems.size() > 0) {
            //如果大于  不删除购物车 更新购物车数据
            updateCart(memberVo.getId(), cart, cartItems);
        } else {
            //删除当前用户购物车中的数据
            RedisUtil.hdel(SystemConstant.CARTNAME, KeyUtil.buildCartLtem(memberVo.getId()));
        }
        //把当前订单总计和总个数放入map中 传到前台
        Map map = new HashMap();
        map.put("orderItems", orderItems);
        map.put("order", order);
        return ServerResponse.success(ResponseEnum.SUCCESS, map);
    }


    void updateCart(Long id, Cart cart, List<CartItem> cartItems) {
        BigDecimal totalPrice = new BigDecimal(0);
        Long totalCount = 0l;
        for (int i = cartItems.size() - 1; i >= 0; i--) {
            CartItem cartItem = cartItems.get(i);
            totalPrice = BigDecimalUtil.add(totalPrice.toString(), cartItem.getSubTotalPrice());
            totalCount += cartItem.getSubTotalCount();
        }
        cart.setTotalCount(totalCount);
        cart.setTotalPrice(totalPrice.toString());
        //重新赋值 把库存不够的商品保存到redis中
        cart.setList(cartItems);
        String jsonString = JSONObject.toJSONString(cart);
        RedisUtil.hset(SystemConstant.CARTNAME, KeyUtil.buildCartLtem(id), jsonString);
    }
}
