package com.red.dust.billowing.utils;

import com.red.dust.billowing.common.Contents;

public class BaseUtils {

    public static String appendCosUrl(String url){
        StringBuffer sb = new StringBuffer().append(Contents.COS_URL).append(url);
        return sb.toString();
    }
}
