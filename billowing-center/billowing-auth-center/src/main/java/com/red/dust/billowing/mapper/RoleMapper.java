package com.red.dust.billowing.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.red.dust.billowing.entity.IRole;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper extends BaseMapper<IRole> {
}
