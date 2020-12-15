package com.red.dust.billowing.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.dust.billowing.entity.IUser;
import com.red.dust.billowing.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<IUser> {

    UserInfoVO getByOpenId(@Param("openid") String openid);
}
