package com.red.dust.billowing.service;

import com.red.dust.billowing.common.Contents;
import com.red.dust.billowing.service.impl.IAuthService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthService implements IAuthService {

    /**
     * jwt 对称加密密钥
     */
    @Value("${spring.security.oauth2.jwt.signingKey}")
    private String signingKey;

    @Override
    public Jws<Claims> getJwt(String jwtToken) {
        if(jwtToken.startsWith(Contents.BEARER)){
            jwtToken = StringUtils.substring(jwtToken,Contents.BEARER.length());
        }
        return Jwts.parser().setSigningKey(signingKey.getBytes()) //设置签名的秘钥
                .parseClaimsJws(jwtToken);
    }

    @Override
    public boolean invalidJwtAccessToken(String authentication) {
        boolean invalid = Boolean.TRUE;
        try{
            getJwt(authentication);
            invalid = Boolean.FALSE;
        }catch (SignatureException| ExpiredJwtException|MalformedJwtException ex){
            log.error("user token error:{}",ex.getMessage());
        }
        return invalid;
    }
}
