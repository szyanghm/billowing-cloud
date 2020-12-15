package com.red.dust.billowing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.dust.billowing.entity.IUser;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UsersMapper extends BaseMapper<IUser> {


}
