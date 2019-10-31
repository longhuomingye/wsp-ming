package com.fh.shop.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {
    /**
     * 欢迎界面
     * @return
     */
    @RequestMapping("/indexJsp")
    public String indexJsp(){
        return "/index";
    }
}
