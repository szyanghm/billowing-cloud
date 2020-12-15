package com.red.dust.billowing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.red.dust.billowing.dto.ParameterDTO;
import com.red.dust.billowing.entity.IMiniUser;
import com.red.dust.billowing.vo.CommentVO;
import com.red.dust.billowing.vo.ResultVO;
import com.red.dust.billowing.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MiniUserMapper extends BaseMapper<IMiniUser> {

    /**
     * 查询用户信息
     * @param dto
     * @return
     */
    UserInfoVO getByOpenIdUser(ParameterDTO dto);

    /**
     * 查询用户标签
     * @param id
     * @return
     */
    List<String> findTagList(@Param("miniId") String id);

    /**
     * 查询用户评论
     * @param page
     * @param id ->cover_mini_id
     * @return
     */
    IPage<CommentVO> findPageComment(Page page, @Param("miniId") String id,@Param("giveMiniId")String giveMiniId);

    /**
     * 查询用户关注
     * @param miniId
     * @param coverMiniId
     * @return
     */
    Integer getByFollowState(@Param("miniId") String miniId,@Param("coverMiniId")String coverMiniId);

    /**
     * 查询用户粉丝
     * @param miniId
     * @param miniId
     * @return
     */
    Integer getByFansNum(@Param("miniId") String miniId);

    /**
     * 关注操作
     * @param dto
     * @return
     */
    int saveFollow(ParameterDTO dto);

    /**
     * 保持粉丝
     * @param miniId
     * @param fansNum
     * @return
     */
    int saveFans(@Param("miniId")String miniId,@Param("fansNum") int fansNum);

    /**
     * 查询用户详情图片
     * @param miniId
     * @return
     */
    List<Map<String,String>> findUserDetailList(@Param("miniId") String miniId);

    /**
     * 保存用户详情图片
     * @param dto
     * @return
     */
    int saveMiniUserDetail(ParameterDTO dto);

    /**
     * 删除用户详情图片
     * @param miniId
     * @return
     */
    int deleteMiniUserDetail(@Param("miniId") String miniId);
}
