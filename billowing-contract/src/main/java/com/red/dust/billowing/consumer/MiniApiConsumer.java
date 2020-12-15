package com.red.dust.billowing.consumer;


import com.red.dust.billowing.dto.ParameterDTO;
import com.red.dust.billowing.entity.IMiniUser;
import com.red.dust.billowing.vo.MiniUserVO;
import com.red.dust.billowing.vo.PageVO;
import com.red.dust.billowing.vo.ResultVO;
import com.red.dust.billowing.vo.UserInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "billowing-service")
@RequestMapping(value = "/service")
public interface MiniApiConsumer {

    @PostMapping(value = "/mini/addUser")
    ResultVO<IMiniUser> addUser(@RequestBody MiniUserVO vo);

    /**
     * 查询用户基本信息
     * @param dto
     * @return
     */
    @PostMapping("/mini/info")
    ResultVO<UserInfoVO> getUserInfo(@RequestBody ParameterDTO dto);

    @PostMapping(value = "/mini/saveOrUpdate")
    ResultVO saveOrUpdate(@RequestBody MiniUserVO vo);

    /**
     * 查询评论列表
     * @param vo
     * @return
     */
    @PostMapping(value = "/mini/findComment")
    ResultVO findComment(@RequestBody PageVO<String> vo);


    /**
     * 关注操作
     * @param dto
     * @return
     */
    @PostMapping(value = "/mini/saveFollow")
    ResultVO saveFollow(@RequestBody ParameterDTO dto);

    /**
     * 更新用户详情图片
     * @param dto
     * @return
     */
    @PostMapping(value = "/mini/saveUserImage")
    ResultVO saveMiniUserDetail(@RequestBody ParameterDTO dto);

    /**
     * 保存评论点赞
     * @param dto
     * @return
     */
    @PostMapping(value = "/mini/saveCommentGive")
    ResultVO saveCommentGive(@RequestBody ParameterDTO dto);

    /**
     * 保存评论
     * @param dto
     * @return
     */
    @PostMapping(value = "/mini/saveComment")
    ResultVO saveComment(@RequestBody ParameterDTO dto);
}
