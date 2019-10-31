package com.fh.shop.api.product.controller;

import com.fh.shop.api.product.biz.IProductService;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.product.po.Product;
import com.fh.shop.api.util.FileUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductController {

    @Resource(name = "productService")
    private IProductService productService;

    @RequestMapping("findListAll")
    @ApiOperation(value = "这是查询所有上架的商品的接口",httpMethod = "post",notes = "公共资源")
    @ApiParam(name = "需要的参数：无",value = "不需要参数")
    public Object findListAll(String callback) {
        ServerResponse listAll = productService.findListAll(callback);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(listAll);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }


    @Scheduled(cron = "0 0 0 * * ? ")//秒、分、时、日、月、周、年（可选）
    public void time() throws Exception {
        List<Product> list = productService.findProductByStatus();
        String str = "";
        for (Product product : list) {
            str += "商品名称:" + product.getProductName() + ",";
            str += "价格:" + product.getPrice().toString() + ",";
            str += "库存:" + product.getStock() + ";";
        }
        FileUtil.FaYouJian("532028476@qq.com", str, "王明夜");
    }
}
