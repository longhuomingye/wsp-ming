package com.fh.shop.admin.biz.product;

import com.fh.shop.admin.mapper.product.IProductMapper;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.test.AreaTest;
import com.fh.shop.admin.util.FileUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-common.xml"})
public class TestProdcut extends AbstractJUnit4SpringContextTests {

    @Autowired
    private IProductMapper productMapper;

    @Test
    public void add(){
        Product product = new Product();
        product.setProductName("1561651");
        productMapper.insert(product);
    }
    @Test
    public void deleteByid(){
        Integer id = 6;
        productMapper.deleteProduct(3);
    }
    @Test
    public void list(){
        Integer count = productMapper.selectCount(null);
        System.out.println(count);
    }
    @Test
    public void sss(){
        AreaTest areaTest = new AreaTest(0,"试试");
        System.out.println(areaTest.getName());
    }



}
