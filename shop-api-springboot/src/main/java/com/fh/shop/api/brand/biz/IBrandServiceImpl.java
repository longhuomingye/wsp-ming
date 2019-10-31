package com.fh.shop.api.brand.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.brand.mapper.IBrandMapper;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("brandService")
@Transactional(rollbackFor = Exception.class)
public class IBrandServiceImpl implements IBrandService {

    @Autowired
    private IBrandMapper brandMapper;

    @Transactional(readOnly = true)
    @Override
    public ServerResponse findBrandBySellState() {
        String hotBrandListJson = RedisUtil.get("hotBrandList");
        //判断当前数据 在redis中是否存在  如果存在直接返回前台
        if (StringUtils.isNotEmpty(hotBrandListJson)) {
            //把json格式的字符串 转换为  java对象
            List<Brand> brandList = JSONObject.parseArray(hotBrandListJson, Brand.class);
            return ServerResponse.success(ResponseEnum.SUCCESS, brandList);
        }
        //如果redis中不存在当前数据 从数据库查询
        QueryWrapper<Brand> brandQueryWrapper = new QueryWrapper<>();
        brandQueryWrapper.eq("sellState", 1);
        brandQueryWrapper.orderByAsc("sort");
        List<Brand> brands = brandMapper.selectList(brandQueryWrapper);
        //把java对象转换为json格式的字符串
        String BrandjsonString = JSONObject.toJSONString(brands);
        //保存到redis中
        RedisUtil.setEx("hotBrandList", BrandjsonString, 20);
        return ServerResponse.success(ResponseEnum.SUCCESS, brands);
    }

    @Transactional(readOnly = true)
    @Override
    public ServerResponse findBrand(Brand brand) {
        //查询总条数
        Long count = brandMapper.findBrandByCount(brand);
        //查询对应的数据
        List<Brand> list = brandMapper.findBrand(brand);
        //返回DataTableResult
        DataTableResult dataTableResult = new DataTableResult(brand.getDraw(), count, count, list);
        return ServerResponse.success(ResponseEnum.SUCCESS, dataTableResult);
    }

    @Override
    public ServerResponse addBrand(Brand brand) {
        brandMapper.insert(brand);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse delBrand(Integer id) {
        brandMapper.deleteById(id);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @Transactional(readOnly = true)
    @Override
    public ServerResponse toUpdateBrand(Integer id) {
        Brand brand = brandMapper.selectById(id);
        return ServerResponse.success(ResponseEnum.SUCCESS, brand);
    }

    @Override
    public ServerResponse updateBrand(Brand brand) {
        brandMapper.updateById(brand);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @Override
    public ServerResponse deleteQuanBrand(Integer[] ids) {
        List<Integer> idList = new ArrayList<>();
        for (Integer id : ids) {
            idList.add(id);
        }
        brandMapper.deleteBatchIds(idList);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }
}
