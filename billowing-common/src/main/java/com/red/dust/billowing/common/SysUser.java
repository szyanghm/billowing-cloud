package com.red.dust.billowing.common;

import lombok.Data;

import java.util.Date;

@Data
public class SysUser {

    private boolean isAuth = false; // 是否认证
    private Date loginTime;  // 登录时间
    private String userId;     // 用户主键id
    private String name;    // 昵称
    private String openId;  //用户openId
    private String mobile;  //电话
    private String addr;    // 地址
}
