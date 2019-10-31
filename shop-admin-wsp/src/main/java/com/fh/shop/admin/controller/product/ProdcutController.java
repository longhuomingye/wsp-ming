package com.fh.shop.admin.controller.product;

import com.fh.shop.admin.biz.product.ProductService;
import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.brand.Brand;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.util.SystemConstant;
import com.fh.shop.admin.vo.product.ProductVo;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ProdcutController {
    @Resource(name="productService")
    private ProductService productService;
    @Autowired
    private HttpServletRequest request;
    /**
     * 跳转查询商品页面
     * @return
     */
    @RequestMapping("/toList")
    public String toList(){
        return "/product/productList";
    }

    /**
     * 查询商品
     * @return
     */
    @RequestMapping("/productList")
    @ResponseBody
    public ServerResponse productList(ProductSearchParam productSearchParam){
        DataTableResult dataTableResult = productService.productList(productSearchParam);
        return ServerResponse.success(ResponseEnum.SUCCESS,dataTableResult);
    }

    /**
     * 添加商品
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public ServerResponse add(Product product){
        productService.add(product);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }


    /**
     * 删除商品
     * @param id
     * @param mainImagePath
     * @return
     */
    @RequestMapping("/deleteProduct")
    @ResponseBody
    public ServerResponse deleteProduct(Integer id,String mainImagePath){
        productService.deleteProduct(id,mainImagePath);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @RequestMapping("/toUpdateProduct")
    @ResponseBody
    public ServerResponse toUpdateProduct(Integer id){
        Product product = productService.toUpdateProduct(id);
        return ServerResponse.success(ResponseEnum.SUCCESS,product);
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping("/updateProduct")
    @ResponseBody
    public ServerResponse updateProduct(Product product){
        productService.updateProduct(product);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 上架 下架
     * @param product
     * @return
     */
    @RequestMapping("/updateStatus")
    @ResponseBody
    public ServerResponse updateStatus(Product product){
        productService.updateStatus(product);
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }

    /**
     * 导出excel
     * @param productSearchParam
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(ProductSearchParam productSearchParam, HttpServletResponse response){
        //根据条件查询数据
        List<Product> list = productService.findProductList(productSearchParam);
        //设置需要导出的数据
        String[] propertys = {"id","productName","price","producedDate"};
        //构建表头
        String[] head = {"id","商品名称", "价格","生产日期"};
        FileUtil.bulidExcel(list,Product.class,"商品信息",head,propertys,response);
    }


    /**
     * 导出pdf
     * @param productSearchParam
     */
    @RequestMapping("/exportPdf")
    public void exportPdf(ProductSearchParam productSearchParam,HttpServletResponse response){
        //根据条件查询数据
        List<Product> list = productService.findProductList(productSearchParam);
        //po转vo
        List<ProductVo> productVos = buildProductVo(list);
        // 构建模板数据
        Map data = buildData(productVos);
        // 生成模板对应的html
        String htmlContent = FileUtil.buildPdfHtml(data, SystemConstant.PRODUCT_PDF_TEMPLATE_FILE);
        // 转为pdf并进行下载
        FileUtil.pdfDownloadFile(response, htmlContent);
    }

    private List<ProductVo> buildProductVo(List<Product> list) {
        List<ProductVo> productVos = new ArrayList<>();
        for (Product product : list) {
            ProductVo productVo = new ProductVo();
            productVo.setId(product.getId());
            productVo.setBrand(product.getBrand());
            productVo.setMainImagePath(product.getMainImagePath());
            productVo.setPrice(product.getPrice().toString());
            productVo.setProducedDate(DateUtil.date2str(product.getProducedDate(),DateUtil.Y_M_D));
            productVo.setProductName(product.getProductName());
            productVo.setStatus(product.getStatus());
            productVo.setStock(product.getStock());
            productVo.setSellState(product.getSellState());
            productVos.add(productVo);
        }
        return productVos;
    }


    private Map buildData(List<ProductVo> productList) {
        Map data = new HashMap();
        //单位
        data.put("companyName", SystemConstant.COMPANY_NAME);
        //数据
        data.put("products", productList);
        //导出的时间
        data.put("createDate", DateUtil.date2str(new Date(), DateUtil.Y_M_D));
        return data;
    }

    /**
     * 导出word
     * @param productSearchParam
     * @param response
     */
    @RequestMapping(value = "/exportWord")
    public void exportWord(ProductSearchParam productSearchParam, HttpServletResponse response){
        File file = productService.buildWordFile(productSearchParam);
        // 调用下载方法
        FileUtil.downloadFile(request,response,file.getPath(),file.getName());
        //删除垃圾文件
        file.delete();
    }



}
