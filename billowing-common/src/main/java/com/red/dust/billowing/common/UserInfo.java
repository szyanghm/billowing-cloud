package com.red.dust.billowing.common;

import io.netty.channel.Channel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Andy
 */
@Getter
@Setter
public class UserInfo {

    private boolean isAuth = false; // 是否认证
    private long time = 0;  // 登录时间
    private String userId;     // UID
    private String nickName;    // 昵称
    private String avatarUrl;   //头像
    private String addr;    // 地址
    private Channel channel;// 通道

}
