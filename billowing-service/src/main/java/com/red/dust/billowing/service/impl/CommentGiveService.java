package com.red.dust.billowing.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.red.dust.billowing.common.LocalProvider;
import com.red.dust.billowing.common.SysUser;
import com.red.dust.billowing.dto.ParameterDTO;
import com.red.dust.billowing.entity.ICommentGive;
import com.red.dust.billowing.mapper.CommentGiveMapper;
import com.red.dust.billowing.service.ICommentGiveService;
import com.red.dust.billowing.vo.ResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Service
@DS("admin")
public class CommentGiveService extends ServiceImpl<CommentGiveMapper, ICommentGive> implements ICommentGiveService {

    @Override
    public ResultVO addOrUpDate(ParameterDTO dto) {
        if(ObjectUtil.isNull(dto)|| StringUtils.isBlank(dto.getCommentId())){
            return ResultVO.notValid();
        }
        SysUser sysUser = LocalProvider.getUser();
        dto.setMiniId(sysUser.getUserId());
        dto.setId(dto.getCommentId()+sysUser.getUserId());
        int giveUp = -1;
        if(dto.getState()==1){
            giveUp =1;
        }
        dto.setNum(giveUp);
        if(baseMapper.addOrUpDate(dto)>0&&baseMapper.updateCommentGiveUp(dto)>0){
            return ResultVO.success();
        }
        return ResultVO.fail();
    }

    public ResultVO saveComment(ParameterDTO dto){
        if(ObjectUtil.isNull(dto)|| StringUtils.isBlank(dto.getCoverMiniId())||StringUtils.isBlank(dto.getMess())){
            return ResultVO.notValid();
        }
        SysUser sysUser = LocalProvider.getUser();
        dto.setMiniId(sysUser.getUserId());
        if(baseMapper.saveComment(dto)>0){
            return ResultVO.success();
        }
        return ResultVO.fail();
    }
}
