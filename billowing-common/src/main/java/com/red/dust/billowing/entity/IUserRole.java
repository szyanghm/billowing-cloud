package com.red.dust.billowing.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.red.dust.billowing.base.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_role_relation")
public class IUserRole extends BasePo {

    private String userId;
    private String roleId;

}
