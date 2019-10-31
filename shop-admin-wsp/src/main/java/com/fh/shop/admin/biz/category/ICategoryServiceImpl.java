package com.fh.shop.admin.biz.category;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.admin.mapper.Category.ICategoryMapper;
import com.fh.shop.admin.po.category.Category;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.vo.Category.CategoryVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("categoryService")
public class ICategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryMapper categoryMapper;

    @Override
    public List<CategoryVo> findCategoryList() {
        String categoryListJson = RedisUtil.get("categoryList");
        if(StringUtils.isNotEmpty(categoryListJson)){
            //转换为java对象
            List<Category> categories = JSONObject.parseArray(categoryListJson, Category.class);
            //po转vo
            List<CategoryVo> voList = buildCategoryVo(categories);
            return voList;
        }
        List<Category> list = categoryMapper.findCategoryList();
        //po转vo
        List<CategoryVo> voList = buildCategoryVo(list);
        return voList;
    }

    @Override
    public void add(Category category) {
        RedisUtil.del("categoryList");
        categoryMapper.insert(category);
    }

    @Override
    public void updateCategory(Category category) {
        RedisUtil.del("categoryList");
        categoryMapper.updateById(category);
    }

    @Override
    public void deleteCategory(Integer[] ids) {
        RedisUtil.del("categoryList");
        categoryMapper.deleteById(ids);
    }

    @Override
    public List<Category> findCategorySelect(Integer id) {
        String categoryListJson = RedisUtil.get("categoryList");
        List<Category> list = null;
        //判断当前字符串是否  不为空
        if(StringUtils.isNotEmpty(categoryListJson)){
            //转换为java对象
            List<Category> categories = JSONObject.parseArray(categoryListJson, Category.class);
            //筛选符合条件的数据
            list = buildCategoryById(id, categories);
        }else{
            //为空  从数据库查询
            QueryWrapper<Category> cqw = new QueryWrapper<>();
            List<Category> categories = categoryMapper.selectList(cqw);
            //保存数据到redis  转换为json字符串保存
            String jsonString = JSONObject.toJSONString(categories);
            //保存
            RedisUtil.set("categoryList",jsonString);
            //筛选符合条件的数据
            list = buildCategoryById(id,categories);
        }
        return list;
    }

    private List<CategoryVo> buildCategoryVo(List<Category> list) {
        List<CategoryVo> voList = new ArrayList<>();
        for (Category c : list) {
            CategoryVo categoryVo = new CategoryVo();
            categoryVo.setId(c.getId());
            categoryVo.setName(c.getCategoryName());
            categoryVo.setpId(c.getFatherId());
            voList.add(categoryVo);
        }
        return voList;
    }

    private List<Category> buildCategoryById(Integer id,List<Category> categories){
        List<Category> list = new ArrayList<>();
        for (Category category : categories) {
            if(category.getFatherId()==id){
                list.add(category);
            }
        }
        return list;
    }
}
