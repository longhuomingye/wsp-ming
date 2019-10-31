package com.fh.shop.api.category.biz;

import com.fh.shop.api.category.po.Category;

import java.util.List;

public interface ICategoryService {
    List<Category> findCategoryList();
}
