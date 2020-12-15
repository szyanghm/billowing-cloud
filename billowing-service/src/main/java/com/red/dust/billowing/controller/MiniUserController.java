package com.red.dust.billowing.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.dust.billowing.dto.ParameterDTO;
import com.red.dust.billowing.entity.IMiniUser;
import com.red.dust.billowing.service.impl.CommentGiveService;
import com.red.dust.billowing.service.impl.MiniService;
import com.red.dust.billowing.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mini")
public class MiniUserController {

    @Autowired
    private MiniService miniService;

    @Autowired
    private CommentGiveService commentGiveService;

    /**
     * 新增用户
     * @param vo
     * @return
     */
    @PostMapping(value = "/addUser")
    public ResultVO<IMiniUser> addUser(@RequestBody MiniUserVO vo){
        IMiniUser user = new IMiniUser();
        BeanUtils.copyProperties(vo,user);
        return miniService.addUser(user);
    }

    /**
     * 获取用户信息
     * @param dto
     * @return
     */
    @PostMapping("/info")
    public ResultVO<UserInfoVO> getUserInfo(@RequestBody ParameterDTO dto){
        UserInfoVO vo = miniService.getByOpenIdUser(dto);
        return ResultVO.success(vo);
    }

    /**
     * 保存更新用户信息
     * @param vo
     * @return
     */
    @PostMapping(value = "/saveOrUpdate")
    public ResultVO saveOrUpdate(@RequestBody MiniUserVO vo){
        IMiniUser user = new IMiniUser();
        BeanUtils.copyProperties(vo,user);
        if(miniService.saveOrUpdate(user)){
            return ResultVO.success();
        }
        return ResultVO.fail();
    }

    /**
     * 查询评论列表
     * @param vo
     * @return
     */
    @PostMapping(value = "/findComment")
    public ResultVO findComment(@RequestBody PageVO<String> vo){
        IPage<CommentVO> iPage = miniService.findPageComment(new Page(vo.getPageNo(),vo.getPageSize()),vo.getData());
        return ResultVO.success(iPage);
    }

    /**
     * 保存关注
     * @param dto
     * @return
     */
    @PostMapping(value = "/saveFollow")
    public ResultVO saveFollow(@RequestBody ParameterDTO dto){
        return miniService.saveFollow(dto);
    }

    /**
     * 保存用户详情图片
     * @param dto
     * @return
     */
    @PostMapping(value = "/saveUserImage")
    public ResultVO saveMiniUserDetail(@RequestBody ParameterDTO dto){
        return miniService.saveMiniUserDetail(dto);
    }

    @PostMapping(value = "/saveCommentGive")
    public ResultVO saveCommentGive(@RequestBody ParameterDTO dto){
        return commentGiveService.addOrUpDate(dto);
    }

    /**
     * 保存评论
     * @param dto
     * @return
     */
    @PostMapping(value = "/saveComment")
    public ResultVO saveComment(@RequestBody ParameterDTO dto){
        return commentGiveService.saveComment(dto);
    }
}
