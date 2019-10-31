package com.fh.shop.api.category.biz;

import com.fh.shop.api.category.mapper.ICategoryMapper;
import com.fh.shop.api.category.po.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("categoryService")
@Transactional(rollbackFor = Exception.class)
public class ICategoryServiceImpl implements ICategoryService {

    @Autowired
    private ICategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Category> findCategoryList() {
        return categoryMapper.selectList(null);
    }
}
