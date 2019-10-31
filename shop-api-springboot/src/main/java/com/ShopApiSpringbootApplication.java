package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@MapperScan("com.fh.shop.api.*.mapper")
@EnableScheduling//定时器总开关
@EnableSwagger2//支持swagger2插件配置
public class ShopApiSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApiSpringbootApplication.class, args);
    }

}
