package com.red.dust.billowing.common;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class LocalProvider {

    private static ContextProvider contextProvider;
    private static final ThreadLocal<SysUser> USER_INFO = new ThreadLocal();

    public static void init(HttpServletRequest request, HttpServletResponse response,SysUser sysUser){
        contextProvider = new ContextProvider(request,response);
        if(ObjectUtil.isNotNull(sysUser)){
            USER_INFO.set(sysUser);
        }
    }

    public static void destroy(){
        USER_INFO.remove();
    }

    public static SysUser getUser(){
        return USER_INFO.get();
    }

    public static HttpServletRequest getRequest(){
        return contextProvider.getRequest();
    }

    public static HttpServletResponse getResponse(){
        return contextProvider.getResponse();
    }
}
