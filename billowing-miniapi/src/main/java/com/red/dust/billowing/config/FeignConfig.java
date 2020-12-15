package com.red.dust.billowing.config;

import com.red.dust.billowing.common.Contents;
import feign.RequestInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Configuration
@Slf4j
public class FeignConfig {

    @Bean
    public RequestInterceptor headerInterceptor(){
        return requestTemplate -> {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(attributes==null){
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader(Contents.AUTHORIZATION);
            if(StringUtils.isNotBlank(token)){
                requestTemplate.header(Contents.AUTHORIZATION,token);
            }
        };
    }
}
