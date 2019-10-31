package com.fh.shop.admin.controller.category;

import com.baomidou.mybatisplus.extension.api.R;
import com.fh.shop.admin.biz.category.ICategoryService;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.po.category.Category;
import com.fh.shop.admin.vo.Category.CategoryVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Resource(name="categoryService")
    private ICategoryService categoryService;

    @RequestMapping("/toList")
    public String toList(){
        return "/category/categoryList";
    }

    /**
     * 查询
     * @return
     */
    @RequestMapping("/findCategoryList")
    @ResponseBody
    public ServerResponse findCategoryList(){
        List<CategoryVo> list = categoryService.findCategoryList();
        return ServerResponse.success(ResponseEnum.SUCCESS,list);
    }

    /**
     * 添加
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(Category category){
        categoryService.add(category);
        return ServerResponse.success(ResponseEnum.SUCCESS,category.getId());
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping("/updateCategory")
    @ResponseBody
    public ServerResponse updateCategory(Category category){
        categoryService.updateCategory(category);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping("/deleteCategory")
    @ResponseBody
    public ServerResponse deleteCategory(Integer[] ids){
        categoryService.deleteCategory(ids);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    @RequestMapping("/findCategorySelect")
    @ResponseBody
    public ServerResponse findCategorySelect(Integer id){
        List<Category> categorySelect = categoryService.findCategorySelect(id);
        return ServerResponse.success(ResponseEnum.SUCCESS,categorySelect);
    }
}
