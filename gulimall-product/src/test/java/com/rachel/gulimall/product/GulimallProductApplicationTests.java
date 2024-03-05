//package com.rachel.gulimall.product;
//
//import com.aliyun.oss.OSS;
//import com.aliyun.oss.OSSClient;
//import com.aliyun.oss.OSSClientBuilder;
//import com.rachel.gulimall.product.entity.BrandEntity;
//import com.rachel.gulimall.product.service.BrandService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.InputStream;
//
//@SpringBootTest
//class GulimallProductApplicationTests {
//
//    @Autowired
//    private BrandService brandService;
//
//    @Autowired
//    private OSSClient ossClient;
//
//    @Test
//    void contextLoads() {
//        BrandEntity brandEntity = new BrandEntity();
//        brandEntity.setName("iphone");
//        brandService.save(brandEntity);
//    }
//
//    @Test
//    public void testUpload() throws FileNotFoundException {
////        String endpoint = "http://oss-cn-chengdu.aliyuncs.com";
////        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
////        String accessKeyId = "LTAI5tD4KsVYDW6MTdkbYiiu";
////        String accessKeySecret = "1IEnE732DHSAyUvtbJ60gvwC2X01hr";
//        // 填写Bucket名称，例如examplebucket。
//        String bucketName = "edu-online-yhc";
//        // 填写Object完整路径，完整路径中不能包含Bucket名称，例如exampledir/exampleobject.txt。
//        String objectName = "测试阿里云2.jpg";
//        // 填写本地文件的完整路径，例如D:\\localpath\\examplefile.txt。
//        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
//        String filePath= "/Users/rachel/Desktop/351691323407_.pic.jpg";
//        // 创建OSSClient实例。
////        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        InputStream inputStream = new FileInputStream(filePath);
//        // 创建PutObject请求。
//        ossClient.putObject(bucketName, objectName, inputStream);
//        //关闭OSSClient
//        ossClient.shutdown();
//        System.out.println("上传完成");
//    }
//
//
//
//}
