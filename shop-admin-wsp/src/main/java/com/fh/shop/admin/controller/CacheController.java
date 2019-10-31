package com.fh.shop.admin.controller;

import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.util.RedisUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("cache")
@RestController
public class CacheController {

    /**
     * 手动清除商品的缓存
     * @return
     */
    @RequestMapping("/sweepCacheProduct")
    public ServerResponse sweepCacheProduct(){
        RedisUtil.del("productList");
        return ServerResponse.success(ResponseEnum.SUCCESS);
    }
}
