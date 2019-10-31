package com.fh.shop.admin.biz.brand;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.brand.Brand;


public interface IBrandService {
    void add(Brand brand);


    DataTableResult list(Brand brand);

    void delete(Integer id);

    Brand findBrand(Integer id);

    void update(Brand brand,String rawbrandImg);

    void updateSellState(Brand brand);

    ServerResponse updateBrandSort(Brand brand);

    ServerResponse findBrandList();
}
