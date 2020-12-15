package com.red.dust.billowing.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.red.dust.billowing.common.LocalProvider;
import com.red.dust.billowing.common.SysUser;
import com.red.dust.billowing.consumer.MiniApiConsumer;
import com.red.dust.billowing.dto.ParameterDTO;
import com.red.dust.billowing.vo.MiniUserVO;
import com.red.dust.billowing.vo.PageVO;
import com.red.dust.billowing.vo.ResultVO;
import com.red.dust.billowing.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private MiniApiConsumer miniApiConsumer;

    /**
     * 获取用户信息
     * @param dto
     * @return
     */
    @PostMapping("/info")
    public ResultVO<UserInfoVO> userInfo(@RequestBody ParameterDTO dto) {
        if(ObjectUtil.isNull(dto)){
            return ResultVO.fail();
        }
        dto.setFlay(true);
        ResultVO<UserInfoVO> resultVO = miniApiConsumer.getUserInfo(dto);
        if (resultVO.getCode().equals(ResultVO.SUCCESSFUL_CODE)) {
            UserInfoVO vo = resultVO.getData();
            return ResultVO.success(vo);
        }
        return ResultVO.fail();
    }

    @PostMapping("/add")
    public ResultVO<UserInfoVO> addUser(@RequestBody UserInfoVO vo) {
        MiniUserVO miniUserVO = new MiniUserVO();
        BeanUtils.copyProperties(miniUserVO,vo);
        miniUserVO.setNickName(vo.getNickName());
        miniApiConsumer.addUser(miniUserVO);
        return ResultVO.fail();
    }

    @PostMapping(value = "/saveOrUpdate")
    public ResultVO saveOrUpdate(@RequestBody MiniUserVO vo){
        return miniApiConsumer.saveOrUpdate(vo);
    }

    /**
     * 查询评论列表
     * @param vo
     * @return
     */
    @PostMapping(value = "/findComment")
    public ResultVO findComment(@RequestBody PageVO<String> vo){
        return miniApiConsumer.findComment(vo);
    }

    /**
     * 保存评论
     * @param dto
     * @return
     */
    @PostMapping(value = "/saveComment")
    public ResultVO saveComment(@RequestBody ParameterDTO dto){
        return miniApiConsumer.saveComment(dto);
    }


    @PostMapping(value = "/saveFollow")
    public ResultVO saveFollow(@RequestBody ParameterDTO dto){
        return miniApiConsumer.saveFollow(dto);
    }

    @PostMapping(value = "/saveUserImage")
    public ResultVO saveMiniUserDetail(@RequestBody ParameterDTO dto){
        System.out.println(JSONObject.toJSONString(dto));
        if(StringUtils.isEmpty(dto.getMiniId())|| CollectionUtil.isEmpty(dto.getList())){
            return ResultVO.fail();
        }
        return miniApiConsumer.saveMiniUserDetail(dto);
    }

    @PostMapping(value = "/saveCommentGive")
    public ResultVO saveCommentGive(@RequestBody ParameterDTO dto){
        return miniApiConsumer.saveCommentGive(dto);
    }
}
