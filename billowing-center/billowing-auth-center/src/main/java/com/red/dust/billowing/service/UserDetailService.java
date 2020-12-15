package com.red.dust.billowing.service;

import com.red.dust.billowing.common.AuthUser;
import com.red.dust.billowing.entity.IRole;
import com.red.dust.billowing.entity.IUser;
import com.red.dust.billowing.service.impl.IRoleService;
import com.red.dust.billowing.service.impl.IUserService;
import com.red.dust.billowing.vo.UserInfoVO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author haimiyang
 * @date:2020/1/15 16:18
 * @version:1.0
 */
@Data
@Slf4j
@Configuration
public class UserDetailService  implements UserDetailsService {

    @Autowired
    private IRoleService iRoleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 模拟一个用户，替代数据库获取逻辑
        return getUserDetails(username);
    }

    /**
     * 构建userdetails
     * @param username 用户名称
     * @return
     */
    private UserDetails getUserDetails(String username){
        // 模拟一个用户，替代数据库获取逻辑
        IUser user = userService.getByUniqueId(username);
        UserInfoVO userInfoVO = userService.getByOpenId(username);
        // 构造security用户
        return new AuthUser(user.getLoginTime(), userInfoVO.getId(),user.getName(),user.getAddr(),user.getMobile(), user.getUsername(),
                user.getPassword(), user.isEnabled(),
                user.isAccountNonExpired(), user.isCredentialsNonExpired(),
                user.isAccountNonLocked(), this.obtainGrantedAuthorities(user));
    }


    /**
     * 获得登录者所有角色的权限集合.
     *
     * @param user
     * @return
     */
    protected Set<GrantedAuthority> obtainGrantedAuthorities(IUser user) {
        Set<IRole> roles = iRoleService.query(user.getId());
        log.info("user:{},roles:{}", user.getUsername(), roles);
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getCode())).collect(Collectors.toSet());
    }

}
