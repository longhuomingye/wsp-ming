package com.fh.shop.admin.biz.brand;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.mapper.brand.IBrandMapper;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.util.OSSUtil;
import com.fh.shop.admin.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "brandService")
public class IBrandServiceImpl implements IBrandService {
    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public void add(Brand brand) {
        //写操作之后 清除缓存
        RedisUtil.del("brandList");
        brandMapper.add(brand);
    }


    @Override
    public DataTableResult list(Brand brand) {
        //查询总条数
        Long recordsTotal = brandMapper.getRecordsTotal();
        List<Brand> list = brandMapper.list(brand);
        DataTableResult dataTableResult = new DataTableResult(brand.getDraw(), recordsTotal, recordsTotal, list);
        return dataTableResult;
    }

    /**
     * 删除单个品牌
     * @param id
     */
    @Override
    public void delete(Integer id) {
        //清除缓存
        RedisUtil.del("brandList");
        brandMapper.delete(id);
    }

    @Override
    public Brand findBrand(Integer id) {
        return brandMapper.findBrand(id);
    }

    /**
     * 修改品牌信息
     * @param brand
     */
    @Override
    public void update(Brand brand,String rawbrandImg) {
        if(StringUtils.isNotEmpty(rawbrandImg) && StringUtils.isNotEmpty(brand.getBrandImg())){
            OSSUtil.deleteOss(rawbrandImg);
        }
        //清除缓存
        RedisUtil.del("brandList");
        brandMapper.update(brand);
    }

    /**
     * 设置品牌热销状态
     * @param brand
     */
    @Override
    public void updateSellState(Brand brand) {
        //清除缓存
        RedisUtil.del("brandList");
        brandMapper.updateSellState(brand);
    }

    /**
     * 设置品牌排序字段
     * @param brand
     * @return
     */
    @Override
    public ServerResponse updateBrandSort(Brand brand) {
        //清除缓存
        RedisUtil.del("brandList");
        brandMapper.updateById(brand);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 查询所有的品牌
     * @return
     */
    @Override
    public ServerResponse findBrandList() {
        String brandList = RedisUtil.get("brandList");
        //判断redis中是否有对应的数据
        if(StringUtils.isEmpty(brandList)){
            //如果没有  从数据库中查询
            List<Brand> brands = brandMapper.findBrandList();
            //将java对象 转换为json格式的字符串
            String str = JSONObject.toJSONString(brands);
            //查询到数据 添加到redis
            RedisUtil.set("brandList",str);
            return ServerResponse.success(ResponseEnum.SUCCESS,brands);
        }else{
            //将json格式的字符串转换为java对象
            List<Brand> brands = JSONObject.parseArray(brandList, Brand.class);
            return ServerResponse.success(ResponseEnum.SUCCESS,brands);
        }
    }

}
