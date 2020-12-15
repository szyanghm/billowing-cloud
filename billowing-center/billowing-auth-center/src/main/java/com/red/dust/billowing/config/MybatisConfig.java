package com.red.dust.billowing.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
//    @Bean
    //@Profile({"dev", "test"})// 设置 dev test 环境开启
//    public PerformanceInterceptor performanceInterceptor() {
//        PaginationInterceptor interceptor = new PaginationInterceptor();
//        Properties properties = new Properties();
//        properties.setProperty("format","false");
//        interceptor.setProperties(properties);
//        return new PerformanceInterceptor();
//    }

    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.red.dust.billowing.mapper*");
        return mapperScannerConfigurer;
    }
}
