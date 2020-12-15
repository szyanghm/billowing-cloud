package com.red.dust.billowing.service;

import com.red.dust.billowing.dto.ParameterDTO;
import com.red.dust.billowing.vo.ResultVO;

public interface ICommentGiveService {

    ResultVO addOrUpDate(ParameterDTO dto);

    /**
     * 保存评论
     * @param dto
     * @return
     */
    ResultVO saveComment(ParameterDTO dto);
}
