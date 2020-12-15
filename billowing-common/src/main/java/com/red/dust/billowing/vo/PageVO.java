package com.red.dust.billowing.vo;

import lombok.Data;

@Data
public class PageVO<T> {

    private int pageNo;
    private int pageSize;
    private T data;

}
