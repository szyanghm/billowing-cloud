package com.red.dust.billowing.config;

import com.alibaba.fastjson.JSONObject;
import com.red.dust.billowing.common.AuthUser;
import com.red.dust.billowing.common.SysUser;
import com.red.dust.billowing.entity.IRole;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author haimiyang
 * @date:2020/1/16 11:26
 * @version:1.0
 */
public class JWTokenEnhancer implements TokenEnhancer {

    @Autowired
    private TokenStore jwtTokenStore;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        OAuth2AccessToken auth2AccessToken = jwtTokenStore.getAccessToken(oAuth2Authentication);
        if(auth2AccessToken!=null){
            jwtTokenStore.removeAccessToken(auth2AccessToken);
        }
        Map<String, Object> info = new HashMap<>();
        AuthUser authUser = (AuthUser) oAuth2Authentication.getUserAuthentication().getPrincipal();
        SysUser sysUser = new SysUser();
        if(authUser.isAccountNonExpired()&&authUser.isAccountNonLocked()&&authUser.isEnabled()&&authUser.isCredentialsNonExpired()){
            sysUser.setAuth(true);
        }
        BeanUtils.copyProperties(authUser,sysUser);
        sysUser.setOpenId(authUser.getUsername());
        info.put("user",sysUser);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
        return oAuth2AccessToken;
    }

}
