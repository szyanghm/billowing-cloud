package com.red.dust.billowing.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.red.dust.billowing.base.BasePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("mini_user")
@NoArgsConstructor
@AllArgsConstructor
public class IMiniUser extends BasePo {

    private String openId;
    private String nickName;
    private String gender;
    private String language;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;
    private String usersId;

}
