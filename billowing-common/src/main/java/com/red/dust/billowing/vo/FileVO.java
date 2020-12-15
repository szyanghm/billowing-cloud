package com.red.dust.billowing.vo;

import lombok.Data;

@Data
public class FileVO {

    private Long fileSize;
    private byte[] bytes;
    private String fileName;
    private String extName;
}
