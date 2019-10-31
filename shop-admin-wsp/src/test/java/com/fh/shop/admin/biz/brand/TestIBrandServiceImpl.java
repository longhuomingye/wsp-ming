package com.fh.shop.admin.biz.brand;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.Page;
import com.fh.shop.admin.po.brand.Brand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-common.xml"})
public class TestIBrandServiceImpl extends AbstractJUnit4SpringContextTests {
    @Resource(name = "brandService")
    private IBrandService brandService;

    @Test
    public void add(){

    }

    @Test
    public void update(){

    }

    @Test
    public void delete(){

    }

    @Test
    public void list(){

    }
}
