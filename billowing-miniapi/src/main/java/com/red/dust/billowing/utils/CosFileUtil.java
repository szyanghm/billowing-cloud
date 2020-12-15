package com.red.dust.billowing.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.PutObjectRequest;
import com.red.dust.billowing.common.Contents;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * 腾讯云Cos工具类
 *
 * @author haimiyang
 * @date:2019/6/18 17:07
 * @version:1.0
 */
@Slf4j
@Component
public class CosFileUtil {

    @Value("${cos_bucketName}")//腾讯云COS 指定要上传到的存储桶
    public String bucketName;

    @Autowired
    private COSClient cosClient;

    public String fileUpload(byte[] bytes, String fileUrl) {
        InputStream inputStream = new ByteArrayInputStream(bytes);
        return fileUpload(inputStream, fileUrl);
    }


    /**
     * 上传文件
     *
     * @param inputStream 文件输入流
     * @param fileUrl    文件上传路径
     * @return 访问腾讯云cos文件路径
     */
    public String fileUpload(InputStream inputStream, String fileUrl) {
        log.info("腾讯云cos上传文件:{}", fileUrl);

        log.info("腾讯云cos生成url:{}", fileUrl);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileUrl, inputStream, null);
        try {
            cosClient.putObject(putObjectRequest);
        } catch (CosClientException e) {
            log.error("腾讯云COS上传文件失败,error:{}", e);
        }
        log.info("腾讯云cos上传文件成功！返回的url:{}", fileUrl);
        return fileUrl;
    }

    /**
     * 删除COS上指定路径对象
     * @param key
     */
    public void delete(String key){
        // Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
        cosClient.deleteObject(bucketName, key);
    }

}
