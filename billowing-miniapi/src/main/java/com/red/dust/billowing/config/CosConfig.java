package com.red.dust.billowing.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 腾讯云 COS 创建客户端连接配置
 * @author haimiyang
 * @date:2019/6/18 14:59
 * @version:1.0
 */
@Configuration
public class CosConfig {

    // 1 传入获取到的永久密钥 (cosSecretId, cosSecretKey)
    @Value("${cos_secretId}")//腾讯云COS secretId
    public String cosSecretId;
    @Value("${cos_secretKey}")//腾讯云COS secretKey
    public String cosSecretKey;
    @Value("${cos_region}")//腾讯云对象存储 COS 支持多地域存储，不同地区的默认访问域名不同。
    public String cosRegion;

    @Bean
    public COSClient cosClient(){
        COSCredentials cred = new BasicCOSCredentials(cosSecretId, cosSecretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参阅 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参阅源码或者常见问题 Java SDK 部分
        Region region = new Region(cosRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        return  cosClient;
    }

}
