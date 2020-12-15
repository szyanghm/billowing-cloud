package com.red.dust.billowing.interceptor;

import com.red.dust.billowing.common.Contents;
import com.red.dust.billowing.common.LocalProvider;
import com.red.dust.billowing.common.SysUser;
import com.red.dust.billowing.consumer.AuthApiConsumer;
import com.red.dust.billowing.consumer.MiniApiConsumer;
import com.red.dust.billowing.dto.ParameterDTO;
import com.red.dust.billowing.vo.ResultVO;
import com.red.dust.billowing.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录拦截器
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthApiConsumer authApiConsumer;
    @Autowired
    private MiniApiConsumer miniApiConsumer;
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info(request.getRequestURL() + "===========preHandle===========");
        String token = request.getHeader(Contents.AUTHORIZATION);
        log.info("token:"+token);
        if (StringUtils.isNotBlank(token)) {
            Map<String,String> map = new HashMap<>();
            map.put(Contents.AUTHORIZATION,token);
            ResultVO<SysUser> resultVO  = authApiConsumer.getToken(map);
            if(resultVO.getCode().equals(ResultVO.SUCCESSFUL_CODE)){
                LocalProvider.init(request,response,resultVO.getData());
                return true;
            }
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        if(StringUtils.containsAny(ResultVO.SUCCESSFUL_CODE,"1000","2000")){
            System.out.println(111111111);
        }
    }

    /**
     * DispatcherServlet进行视图的渲染之后
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) {
        LocalProvider.destroy();
    }
}
