package com.red.dust.billowing.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.red.dust.billowing.base.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

/**
 * @author haimiyang
 * @date:2020/1/15 16:27
 * @version:1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("users")
public class IUser extends BasePo {

    private String name;
    private String mobile;
    private String username;
    private String password;
    private String addr;
    private Date loginTime;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    @TableField(exist = false)
    private Set<String> roleIds;
}
