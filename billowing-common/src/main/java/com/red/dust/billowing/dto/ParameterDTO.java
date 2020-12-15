package com.red.dust.billowing.dto;

import com.red.dust.billowing.base.BasePo;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ParameterDTO extends BasePo {

    private String miniId;
    private String coverMiniId;
    private String commentId;
    private String openId;
    private String mess;
    private boolean flay;
    private int state;
    private int num;
    private List<Map<String,String>> list;
}
