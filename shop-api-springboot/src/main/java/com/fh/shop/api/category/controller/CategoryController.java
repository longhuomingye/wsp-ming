package com.fh.shop.api.category.controller;

import com.fh.shop.api.category.biz.ICategoryService;
import com.fh.shop.api.category.po.Category;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {

    @Resource(name = "categoryService")
    private ICategoryService categoryService;

    @RequestMapping("findCategoryList")
    public ServerResponse findCategoryList() {
        List<Category> list = categoryService.findCategoryList();
        return ServerResponse.success(ResponseEnum.SUCCESS, list);
    }
}
