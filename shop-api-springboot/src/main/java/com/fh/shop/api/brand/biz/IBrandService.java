package com.fh.shop.api.brand.biz;

import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.ServerResponse;

public interface IBrandService {
    ServerResponse findBrandBySellState();

    ServerResponse findBrand(Brand brand);

    ServerResponse addBrand(Brand brand);

    ServerResponse delBrand(Integer id);

    ServerResponse toUpdateBrand(Integer id);

    ServerResponse updateBrand(Brand brand);

    ServerResponse deleteQuanBrand(Integer[] ids);
}
