package com.red.dust.billowing.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.red.dust.billowing.entity.IMiniUser;
import com.red.dust.billowing.entity.IUser;
import com.red.dust.billowing.mapper.UsersMapper;
import com.red.dust.billowing.service.IUsersService;
import com.red.dust.billowing.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UsersService extends ServiceImpl<UsersMapper, IUser> implements IUsersService {

    /**
     * 新增oauth2用户
     * @param user
     * @return
     */
    public ResultVO addUser(IUser user){
        if(baseMapper.insert(user)>0){
            return ResultVO.success();
        }
        return ResultVO.fail();
    }
}
