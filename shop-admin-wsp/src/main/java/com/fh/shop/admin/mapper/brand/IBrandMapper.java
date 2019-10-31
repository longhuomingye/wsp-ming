package com.fh.shop.admin.mapper.brand;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.admin.po.brand.Brand;

import java.util.List;

public interface IBrandMapper extends BaseMapper<Brand> {

    void add(Brand brand);

    Long getRecordsTotal();

    List<Brand> list(Brand brand);

    void delete(Integer id);

    Brand findBrand(Integer id);

    void update(Brand brand);

    void updateSellState(Brand brand);

    List<Brand> findBrandList();
}
