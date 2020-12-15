package com.red.dust.billowing.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.red.dust.billowing.base.BasePo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("roles")
public class IRole extends BasePo {

    private String code;
    private String name;
    private String description;

    @TableField(exist = false)
    private Set<String> resourceIds;
}
