package com.fh.shop.api.product.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.product.mapper.IProductMapper;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.product.vo.ProductVo;
import com.fh.shop.api.util.DateUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("productService")
@Transactional(rollbackFor = Exception.class)
public class IProductServiceImpl implements IProductService {

    @Autowired
    private IProductMapper productMapper;

    @Transactional(readOnly = true)
    @Override
    public ServerResponse findListAll(String callback) {
        String productListJson = RedisUtil.get("productList");
        if (StringUtils.isNotEmpty(productListJson)) {
            //如果redis中有对应的值则进行转换  把json格式的字符串转换为java对象
            List<ProductVo> voList = JSONObject.parseArray(productListJson, ProductVo.class);
            return ServerResponse.success(ResponseEnum.SUCCESS, voList);
        }
        //如果redis中不存在对应的值 则从数据库查询
        QueryWrapper<Product> productQueryWrapper = new QueryWrapper<>();
        productQueryWrapper.orderByDesc("id");
        productQueryWrapper.eq("status", 1);
        List<Product> products = productMapper.selectList(productQueryWrapper);
        //po转vo
        List<ProductVo> voList = buildVo(products);
        //转换为json字符串
        String jsonString = JSONObject.toJSONString(voList);
        //同时把数据缓存到redis中
        RedisUtil.setEx("productList", jsonString, 20);
        return ServerResponse.success(ResponseEnum.SUCCESS, voList);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> findProductByStatus() {
        return productMapper.findProductByStatus();
    }

    private List<ProductVo> buildVo(List<Product> products) {
        List<ProductVo> voList = new ArrayList<>();
        for (Product product : products) {
            ProductVo productVo = new ProductVo();
            productVo.setId(product.getId());
            productVo.setMainImagePath(product.getMainImagePath());
            productVo.setPrice(product.getPrice().toString());
            productVo.setProducedDate(DateUtil.date2str(product.getProducedDate(), DateUtil.Y_M_D));
            productVo.setProductName(product.getProductName());
            productVo.setStatus(product.getStatus());
            productVo.setStock(product.getStock());
            productVo.setSellState(product.getSellState());
            voList.add(productVo);
        }
        return voList;
    }
}
