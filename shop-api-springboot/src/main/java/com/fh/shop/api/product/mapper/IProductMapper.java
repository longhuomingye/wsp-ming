package com.fh.shop.api.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.product.po.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IProductMapper extends BaseMapper<Product> {

    Long updateStockById(@Param("subTotalCount") Long subTotalCount, @Param("id") Long id);

    List findProductByStatus();
}
