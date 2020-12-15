package com.red.dust.billowing.common;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.Date;

@Getter
public class AuthUser extends User {

    private Date loginTime;  // 登录时间
    private String userId;     // 用户主键id
    private String name;    // 昵称
    private String mobile;  //电话
    private String addr;    // 地址

    public AuthUser(Date loginTime, String userId, String name, String addr, String mobile, String username, String password, boolean enabled,
                    boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
                    Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.loginTime = loginTime;
        this.userId = userId;
        this.name = name;
        this.addr = addr;
        this.mobile = mobile;
    }
}
