package com.red.dust.billowing.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.red.dust.billowing.common.Contents;
import com.red.dust.billowing.common.LocalProvider;
import com.red.dust.billowing.common.SysUser;
import com.red.dust.billowing.dto.ParameterDTO;
import com.red.dust.billowing.entity.IMiniUser;
import com.red.dust.billowing.entity.IUser;
import com.red.dust.billowing.enums.SystemErrorType;
import com.red.dust.billowing.mapper.MiniUserMapper;
import com.red.dust.billowing.service.IMiniService;
import com.red.dust.billowing.utils.UUIDUtil;
import com.red.dust.billowing.vo.CommentVO;
import com.red.dust.billowing.vo.ResultVO;
import com.red.dust.billowing.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Service
@DS("admin")
public class MiniService extends ServiceImpl<MiniUserMapper, IMiniUser> implements IMiniService {

    public ResultVO<IMiniUser> addUser(IMiniUser user) {
        //新增oauth2用户
        if (ObjectUtil.isEmpty(user)) {
            return ResultVO.fail();
        }
        IMiniUser miniUser = baseMapper.selectOne(new QueryWrapper<IMiniUser>().eq("open_id", user.getOpenId()).eq("deleted","N"));
        if (ObjectUtil.isNotNull(miniUser)) {
            return ResultVO.success(miniUser);
        }
        if (baseMapper.insert(user) > 0) {
            return ResultVO.success(user);
        }
        return ResultVO.fail();
    }

    @Override
    public UserInfoVO getByOpenIdUser(ParameterDTO dto) {
        SysUser sysUser = LocalProvider.getUser();
        String miniId = sysUser.getUserId();
        if(StringUtils.isNotBlank(dto.getMiniId())){
            miniId = dto.getMiniId();
        }
        ParameterDTO queryDto = new ParameterDTO();
        queryDto.setMiniId(miniId);
        UserInfoVO vo = baseMapper.getByOpenIdUser(queryDto);
        if (ObjectUtil.isNotNull(vo) && dto.isFlay()) {
            List<String> list = baseMapper.findTagList(miniId);
            vo.setFollowState(3);
            if(StringUtils.isNotBlank(dto.getMiniId())&&!dto.getMiniId().equals(sysUser.getUserId())){
                Integer followState = baseMapper.getByFollowState(sysUser.getUserId(),dto.getMiniId());
                vo.setFollowState(ObjectUtil.isNull(followState)?0:followState);
            }
            Integer fansNum = baseMapper.getByFansNum(miniId);
            vo.setList(baseMapper.findUserDetailList(miniId));
            vo.setFansNum(ObjectUtil.isNull(fansNum)?0:fansNum);
            vo.setListTag(list);
        }
        return vo;
    }

    @Override
    public IPage<CommentVO> findPageComment(Page page, String id) {
        SysUser sysUser = LocalProvider.getUser();
        return baseMapper.findPageComment(page, id,sysUser.getUserId());
    }

    @Override
    @Transactional
    public ResultVO saveFollow(ParameterDTO dto) {
        SysUser sysUser = LocalProvider.getUser();
        dto.setMiniId(sysUser.getUserId());
        dto.setId(sysUser.getUserId()+dto.getCoverMiniId());
        int fansNum = -1;
        if(dto.getState()==1){
            fansNum = 1;
        }
        if(baseMapper.saveFollow(dto)>0&& baseMapper.saveFans(dto.getCoverMiniId(),fansNum)>0){
            return ResultVO.success();
        }
        return ResultVO.fail();
    }

    @Transactional
    public ResultVO saveMiniUserDetail(ParameterDTO dto){
        baseMapper.deleteMiniUserDetail(dto.getMiniId());
        if(baseMapper.saveMiniUserDetail(dto)>0){
            return ResultVO.success();
        }
        return ResultVO.fail();
    }

}
