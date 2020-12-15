package com.red.dust.billowing.controller;

import com.red.dust.billowing.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MiniApiController {

    @Autowired
    private RedisUtil redisUtil;

}
