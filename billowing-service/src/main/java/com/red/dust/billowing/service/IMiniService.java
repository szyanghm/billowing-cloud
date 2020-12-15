package com.red.dust.billowing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.dust.billowing.dto.ParameterDTO;
import com.red.dust.billowing.entity.IMiniUser;
import com.red.dust.billowing.vo.CommentVO;
import com.red.dust.billowing.vo.ResultVO;
import com.red.dust.billowing.vo.UserInfoVO;
import org.springframework.web.bind.annotation.RequestBody;

public interface IMiniService {

    /**
     * 新增保存小程序用户信息
     * @param user
     * @return
     */
    ResultVO<IMiniUser> addUser(IMiniUser user);


    /**
     * 查询用户基本信息
     * @param dto
     * @return
     */
    UserInfoVO getByOpenIdUser(ParameterDTO dto);

    IPage<CommentVO> findPageComment(Page page, String vo);

    ResultVO saveFollow(ParameterDTO dto);

    ResultVO saveMiniUserDetail(ParameterDTO dto);


}
