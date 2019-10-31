package com.fh.shop.admin.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class OSSUtil {

    private static String OSSDOMIN = "http://1664046428.oss-cn-beijing.aliyuncs.com/";
    /**
     * 上传图片到oss服务器
     * @param myfile
     * @return
     */
    public static String uploadOss(MultipartFile myfile){
        String path = null;
        OSSClient ossClient = null;
        InputStream is = null;
        try {
            //创建ossclient实例  第一个参数 是你的域名  第二个参数 是产品app  第三个是 对应的密匙
            ossClient = new OSSClient("https://oss-cn-beijing.aliyuncs.com", "LTAI4FhSH5YpmBQEraP1nVkD", "jt7ssmZGr6fAem5YT89xBqSPp84EK7");
            // 上传文件流。
            is = myfile.getInputStream();
            //图片原名称
            String fileName = myfile.getOriginalFilename();
            //UUID
            String uuid = UUID.randomUUID().toString();
            fileName = uuid+fileName.substring(fileName.lastIndexOf("."));
            //创建上传Object的Metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("http");
            metadata.setContentDisposition("inline; filename=noavatar_middle.gif");
            path = "img/" + fileName;
            //文件名
            //第一个参数 是 存储空间的名称  第二个是  文件夹+文件名称  fileName 是 文件名称  is  是文件流
            ossClient.putObject("1664046428", path, is,metadata);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if(null!=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            }
            if(null!=ossClient){
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
        return OSSDOMIN+path;
    }

    /**
     * 删除oss服务器上的图片
     * @param fileName
     */
    public static void deleteOss(String fileName){
        OSSClient ossClient = null;
        try {
            ossClient = new OSSClient("https://oss-cn-beijing.aliyuncs.com", "LTAI4FhSH5YpmBQEraP1nVkD", "jt7ssmZGr6fAem5YT89xBqSPp84EK7");
            fileName.replace(OSSDOMIN,"");
            // ossKey:上传文件时阿里云返回的标识   ：FeedBack/xx/iOS_xxx_35671549541105750.jpg 这个就是文件标识
            ossClient.deleteObject("1664046428", fileName);
        } catch (OSSException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        } finally {
            if(null != ossClient){
                // 关闭OSSClient。
                ossClient.shutdown();
            }
        }
    }
}
