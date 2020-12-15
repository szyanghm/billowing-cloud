package com.red.dust.billowing.service.impl;

import com.red.dust.billowing.entity.IUser;
import com.red.dust.billowing.vo.MiniUserVO;
import com.red.dust.billowing.vo.ResultVO;
import com.red.dust.billowing.vo.UserInfoVO;
import org.apache.ibatis.annotations.Param;

public interface IUserService {

    IUser getByUniqueId(String uniqueId);

    ResultVO<String> addUser(MiniUserVO vo);

    UserInfoVO getByOpenId(String openid);

}
