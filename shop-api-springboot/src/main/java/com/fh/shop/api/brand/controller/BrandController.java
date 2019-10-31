package com.fh.shop.api.brand.controller;

import com.fh.shop.api.brand.biz.IBrandService;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.Check;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/brands")
public class BrandController {

    @Resource(name = "brandService")
    private IBrandService brandService;

    /**
     * 查询热销品牌
     *
     * @return
     */
    @RequestMapping("/findBrandBySellState")
    @Check
    public ServerResponse findBrandBySellState(String callback) {
        ServerResponse serverRequest = brandService.findBrandBySellState();
        return serverRequest;
    }

    /**
     * 分页 on  条件查询
     *
     * @param brand
     * @return
     */
    @GetMapping
    public ServerResponse findBrand(Brand brand) {
        return brandService.findBrand(brand);
    }

    /**
     * 添加商品
     *
     * @param brand
     * @return
     */
    @PostMapping
    public ServerResponse addBrand(Brand brand) {
        return brandService.addBrand(brand);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ServerResponse delBrand(@PathVariable Integer id) {
        return brandService.delBrand(id);
    }

    /**
     * 回显
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ServerResponse toUpdateBrand(@PathVariable Integer id) {
        return brandService.toUpdateBrand(id);
    }

    /**
     * 修改
     *
     * @param brand
     * @return
     */
    @PutMapping
    public ServerResponse updateBrand(@RequestBody Brand brand) {
        return brandService.updateBrand(brand);
    }

    @DeleteMapping
    public ServerResponse deleteQuanBrand(Integer[] ids) {
        return brandService.deleteQuanBrand(ids);
    }
}
