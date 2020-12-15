package com.red.dust.billowing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * @author haimiyang
 * @date:2020/1/16 11:24
 * @version:1.0
 */
@Configuration
public class JWTokenConfig {

    /**
     * jwt 对称加密密钥
     */
    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    @Bean
    public TokenStore jwtTokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(signingKey); // 签名密钥
        return accessTokenConverter;
    }

    @Bean
    @ConditionalOnBean(TokenEnhancer.class)
    public TokenEnhancer tokenEnhancer() {
        return new JWTokenEnhancer();
    }

}
