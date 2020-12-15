package com.red.dust.billowing.config;

import com.red.dust.billowing.exception.CustomWebResponseExceptionTranslator;
import com.red.dust.billowing.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author haimiyang
 * @date:2020/1/15 14:21
 * @version:1.0
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore jwtTokenStore;
    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenEnhancer tokenEnhancer;
    @Autowired
    private UserDetailService userDetailService;

    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(tokenEnhancer);
        enhancers.add(jwtAccessTokenConverter);
        enhancerChain.setTokenEnhancers(enhancers);
        //自定义的tokenServices等信息,配置身份认证器，配置认证方式，TokenStore，OAuth2RequestFactory
        endpoints.tokenEnhancer(enhancerChain).authenticationManager(authenticationManager)
                .exceptionTranslator(customExceptionTranslator())
                .tokenStore(jwtTokenStore)
                .accessTokenConverter(jwtAccessTokenConverter)
                .userDetailsService(userDetailService);
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        // 支持将client参数放在header或body中
        oauthServer.allowFormAuthenticationForClients();
        oauthServer.tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()");
    }

    /**
     * 自定义OAuth2异常处理
     *
     * @return CustomWebResponseExceptionTranslator
     */
    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> customExceptionTranslator() {
        return new CustomWebResponseExceptionTranslator();
    }

    /**
     * 授权信息持久化
     * @return JdbcApprovalStore
     */
    @Bean
    public ApprovalStore approvalStore(){
        return new JdbcApprovalStore(dataSource);
    }

//    @Bean
//    public DefaultTokenServices defaultTokenServices() {
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setAuthenticationManager(this.authenticationManager);
//        defaultTokenServices.setTokenStore(jwtTokenStore);
//        defaultTokenServices.setAccessTokenValiditySeconds(60*60*2);
//        defaultTokenServices.setRefreshTokenValiditySeconds(60*60*24*30);
//        defaultTokenServices.setSupportRefreshToken(true);
//        defaultTokenServices.setReuseRefreshToken(false);
//        return defaultTokenServices;
//    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(dataSource);
//                .inMemory()
//                .withClient("test")
//                .secret("test1234")
//                .authorizedGrantTypes("password", "refresh_token")
//                .accessTokenValiditySeconds(3600)
//                .refreshTokenValiditySeconds(864000)
//                .scopes("all", "a", "b", "c");
    }



}
