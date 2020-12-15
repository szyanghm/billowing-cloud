package com.red.dust.billowing.vo;

import lombok.Data;

@Data
public class CommentVO {

    private String id;
    private String miniId;
    private String nickName;
    private String avatarUrl;
    private String comment;
    private String createdTime;
    private int giveUp;
    private int state;
}
