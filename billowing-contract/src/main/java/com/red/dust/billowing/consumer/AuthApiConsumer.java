package com.red.dust.billowing.consumer;


import com.red.dust.billowing.common.SysUser;
import com.red.dust.billowing.entity.IUser;
import com.red.dust.billowing.vo.MiniUserVO;
import com.red.dust.billowing.vo.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@FeignClient(name = "billowing-auth-center")
@RequestMapping(value = "/user")
public interface AuthApiConsumer {

    @PostMapping("/addUser")
    ResultVO addUser(@RequestBody MiniUserVO vo);

    @PostMapping(value = "/login")
    ResultVO login(@RequestBody IUser user);
    @PostMapping(value = "/getToken")
    ResultVO<SysUser> getToken(@RequestBody Map<String,String> map);

}
