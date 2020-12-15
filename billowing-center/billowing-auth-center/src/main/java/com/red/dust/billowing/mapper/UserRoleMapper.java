package com.red.dust.billowing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.dust.billowing.entity.IUserRole;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<IUserRole> {
}
