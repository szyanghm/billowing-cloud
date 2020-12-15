package com.red.dust.billowing.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.red.dust.billowing.base.BasePo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("comment_give")
@NoArgsConstructor
@AllArgsConstructor
public class ICommentGive extends BasePo {

    private String commentId;
    private String miniId;
    private int state;
}
