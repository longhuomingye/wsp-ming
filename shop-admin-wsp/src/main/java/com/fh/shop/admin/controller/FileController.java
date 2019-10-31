package com.fh.shop.admin.controller;

import com.fh.shop.admin.common.ResponseEnum;
import com.fh.shop.admin.common.ServerResponse;
import com.fh.shop.admin.util.FileUtil;
import com.fh.shop.admin.util.OSSUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {

    @RequestMapping("/uploadFile")
    public ServerResponse uploadFile(MultipartFile myfile) throws IOException {
        String fileName = OSSUtil.uploadOss(myfile);
        return ServerResponse.success(ResponseEnum.SUCCESS,fileName);
    }
}
