package com.red.dust.billowing.controller;

import cn.hutool.http.HttpRequest;
import com.alibaba.fastjson.JSONObject;
import com.red.dust.billowing.common.Contents;
import com.red.dust.billowing.common.SysUser;
import com.red.dust.billowing.entity.IUser;
import com.red.dust.billowing.enums.SystemErrorType;
import com.red.dust.billowing.service.AuthService;
import com.red.dust.billowing.service.UserService;
import com.red.dust.billowing.vo.MiniUserVO;
import com.red.dust.billowing.vo.ResultVO;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haimiyang
 * @date:2020/1/16 10:18
 * @version:1.0
 */
@Slf4j
@RestController
public class UserController {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @GetMapping("index")
    public Object index(@AuthenticationPrincipal Authentication authentication){


        return authentication;
    }

    @PostMapping(value = "/user/login")
    public ResultVO login(@RequestBody IUser user){
        Map<String,String> headersMap = new HashMap<>();
        headersMap.put("Authorization","Basic dGVzdDp0ZXN0MTIzNA==");
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("username",user.getUsername());
        paramMap.put("password",user.getPassword());
        paramMap.put("grant_type","password");
        paramMap.put("scope","all");
        String result = HttpRequest.post("localhost:8070/login")
                .addHeaders(headersMap)
                .form(paramMap).execute().body();
        log.info(JSONObject.toJSONString(result));
        if(result.contains(Contents.AUTH_MSG)){
            return new ResultVO(SystemErrorType.USER_LOCK_ING,result);
        }
        Map<String,String> map = JSONObject.toJavaObject(JSONObject.parseObject(result),Map.class);
        String accessToken = map.get("access_token");
        if(StringUtils.isNotEmpty(accessToken)){
            map = new HashMap<>();
            map.put("access_token",accessToken);
        }
        return ResultVO.success(map);
    }


    @PostMapping(value = "/user/addUser")
    public ResultVO<String> addUser(@RequestBody MiniUserVO vo){
        return userService.addUser(vo);
    }



    @PostMapping(value = "/user/getToken",produces = {"application/json; charset=UTF-8"})
    public ResultVO<SysUser> getToken(@RequestBody Map<String,String> map){
        String jwtToken = map.get(Contents.AUTHORIZATION);
        SysUser sysUser = JSONObject.parseObject(JSONObject.toJSONString(authService.getJwt(jwtToken).getBody().get("user")),SysUser.class);
        log.info("jwtToken解析:"+JSONObject.toJSONString(sysUser));
        return ResultVO.success(sysUser);
    }

}
