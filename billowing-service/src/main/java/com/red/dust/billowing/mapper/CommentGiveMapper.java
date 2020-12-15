package com.red.dust.billowing.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.dust.billowing.dto.ParameterDTO;
import com.red.dust.billowing.entity.ICommentGive;
import org.mapstruct.Mapper;

@Mapper
public interface CommentGiveMapper extends BaseMapper<ICommentGive> {

    /**
     * 保存用户点赞
     * @param dto
     * @return
     */
    int addOrUpDate(ParameterDTO dto);

    /**
     * 更新保存点赞数
     * @param dto
     * @return
     */
    int updateCommentGiveUp(ParameterDTO dto);

    /**
     * 保存评论
     * @param dto
     * @return
     */
    int saveComment(ParameterDTO dto);
}
