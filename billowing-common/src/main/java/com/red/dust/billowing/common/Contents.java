package com.red.dust.billowing.common;

public class Contents {

    /**
     * Authorization认证开头是"bearer"
     */
    public static final String BEARER = "bearer ";

    public static final String BASIC = "Basic ";

    public static final String AUTHORIZATION = "Authorization";

    public static final String ACCESSTOKEN = "access_token";

    public static final int INT_ONE=1;

    public static final int INT_O=0;

    public static final String COS_FOLDER_NAME = "billowing/";

    public static final String COS_URL="https://yhm-1259466197.cos.ap-shenzhen-fsi.myqcloud.com/";


    public static final String PING_CODE = "100015";//netty ping message
    public static final String PONG_CODE = "100016";//receive pong message, address,获取Channel的远程IP地址

    public static final String MESS_CODE = "100086";//Normal message

    public static final String AUTH_MSG = "用户帐号已被锁定";


    /**
     * 系统消息类型
     */
    public static final int SYS_USER_COUNT = 20001; // 在线用户数
    public static final int SYS_AUTH_STATE = 20002; // 认证结果
    public static final int SYS_OTHER_INFO = 20003; // 系统消息

    public static String DEFAULT_HOST = "localhost";
    public static int DEFAULT_PORT = 9688;
    public static String WEBSOCKET_URL = "ws://localhost:8099/websocket";
}
