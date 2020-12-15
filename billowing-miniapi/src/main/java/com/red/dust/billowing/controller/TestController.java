package com.red.dust.billowing.controller;

import com.red.dust.billowing.entity.Test;
import com.red.dust.billowing.utils.RedisUtil;
import com.red.dust.billowing.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private RedisUtil redisUtil;

    @PostMapping(value = "/postTest")
    public ResultVO test(@RequestBody Test test){
        redisUtil.set(test.getId(),test.getName());
        String str = String.valueOf(redisUtil.get(test.getId()));
        System.out.println(str);
        return ResultVO.success(str);
    }
}
