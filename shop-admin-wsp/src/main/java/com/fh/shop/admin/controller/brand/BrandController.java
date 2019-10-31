package com.fh.shop.admin.controller.brand;

import com.fh.shop.admin.biz.brand.IBrandService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.Log;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.brand.Brand;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/brand")
public class BrandController {
    @Resource(name = "brandService")
    private IBrandService brandService;

    /**
     * ajax添加
     * @param brand
     * @return
     */
    @RequestMapping(value = "/addAjax")
    @ResponseBody
    @Log("添加品牌")
    public ServerResponse addAjax(Brand brand){
        brandService.add(brand);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 去列表展示页面
     * @return
     */
    @RequestMapping(value = "/toList")
    public String toList(){
        return "brand/list";
    }

    /**
     * 查询list数据
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public ServerResponse list(Brand brand){
        DataTableResult dataTableResult = brandService.list(brand);
        return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "delete")
    @ResponseBody
    @Log("删除品牌")
    public ServerResponse delete(Integer id){
        brandService.delete(id);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @RequestMapping(value = "findBrand")
    @ResponseBody
    public ServerResponse findBrand(Integer id){
        Brand brand = brandService.findBrand(id);
        return ServerResponse.success(ResponseEnum.SUCCESS,brand);
    }

    /**
     * ajax修改
     * @param brand
     * @return
     */
    @RequestMapping(value = "updateAjax")
    @ResponseBody
    @Log("修改品牌")
    public ServerResponse updateAjax(Brand brand,String rawbrandImg){
        brandService.update(brand,rawbrandImg);
        return ServerResponse.success(ResponseEnum.SUCCESS,brand);
    }

    /**
     * 热销状态
     * @param brand
     * @return
     */
    @RequestMapping("/updateSellState")
    @ResponseBody
    public ServerResponse updateSellState(Brand brand){
        brandService.updateSellState(brand);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @RequestMapping("/updateBrandSort")
    @ResponseBody
    public ServerResponse updateBrandSort(Brand brand){
        return brandService.updateBrandSort(brand);
    }

    @RequestMapping("/findBrandList")
    @ResponseBody
    public ServerResponse findBrandList(){
        return brandService.findBrandList();
    }
}
