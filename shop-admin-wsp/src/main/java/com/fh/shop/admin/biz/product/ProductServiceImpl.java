package com.fh.shop.admin.biz.product;

import com.fh.shop.admin.common.DataTableResult;
import com.fh.shop.admin.mapper.product.IProductMapper;
import com.fh.shop.admin.param.product.ProductSearchParam;
import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.util.DateUtil;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.util.SystemConstant;
import com.fh.shop.admin.vo.product.ProductVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service("productService")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private IProductMapper productMapper;

    /**
     * 查询商品
     * @param productSearchParam
     * @return
     */
    @Override
    public DataTableResult productList(ProductSearchParam productSearchParam) {
        //查询总条数
        Long count = productMapper.findProductByCount(productSearchParam);
        //查询本页数据
        List<Product> list = productMapper.productList(productSearchParam);
        //po转vo
        List<ProductVo> productVos = buildProductVo(list);
        DataTableResult dataTableResult = new DataTableResult(productSearchParam.getDraw(), count, count, productVos);
        return dataTableResult;
    }

    /**
     * 添加商品
     * @param product
     */
    @Override
    public void add(Product product) {
        productMapper.add(product);
    }



    /**
     * 删除商品
     * @param id
     */
    @Override
    public void deleteProduct(Integer id,String mainImagePath) {
        productMapper.deleteProduct(id);
    }

    /**
     * 回显
     * @param id
     * @return
     */
    @Override
    public Product toUpdateProduct(Integer id) {
        Product product = productMapper.toUpdateProduct(id);
        return product;
    }

    /**
     * 修改
     * @param product
     */
    @Override
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    @Override
    public void updateStatus(Product product) {
        if(product.getStatus()==1){
            product.setStatus(2);
        }else{
            product.setStatus(1);
        }
        productMapper.updateStatus(product);
    }

    @Override
    public List<Product> findProductList(ProductSearchParam productSearchParam) {
        if(productSearchParam.getSelectCategory()[0]!=null && productSearchParam.getSelectCategory()[0]>0){
            productSearchParam.setCate1(productSearchParam.getSelectCategory()[0]);
            if(productSearchParam.getSelectCategory()[1]!=null && productSearchParam.getSelectCategory()[0]>0){
                productSearchParam.setCate2(productSearchParam.getSelectCategory()[1]);
                if(productSearchParam.getSelectCategory()[2]!=null && productSearchParam.getSelectCategory()[0]>0){
                    productSearchParam.setCate3(productSearchParam.getSelectCategory()[2]);
                }
            }
        }
        return productMapper.findProductList(productSearchParam);
    }

    @Override
    public File buildWordFile(ProductSearchParam productSearchParam) {
        // 根据条件动态查询数据
        List<Product> productList = findProductList(productSearchParam);
        //po转vo
        List<ProductVo> productVos = buildProductVo(productList);
        // 构建数据
        Map map = buildWordData(productVos);
        // 转换为指定的格式
        File file = FileUtil.buildWord(map, SystemConstant.PRODUCT_WORD_FILE);
        return file;
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
            productVo.setCategoryNames(product.getCategoryNames());
            productVos.add(productVo);
        }
        return productVos;
    }

    private Map buildWordData(List<ProductVo> productList) {
        Map map = new HashMap();
        map.put("products", productList);
        return map;
    }


}
