package com.red.dust.billowing.utils;

import cn.hutool.core.lang.UUID;

public class UUIDUtil {

    public static String generateId(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
