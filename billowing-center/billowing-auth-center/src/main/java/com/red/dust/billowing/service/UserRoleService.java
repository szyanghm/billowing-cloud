package com.red.dust.billowing.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.red.dust.billowing.mapper.UserRoleMapper;
import com.red.dust.billowing.entity.IUserRole;
import com.red.dust.billowing.service.impl.IUserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@DS("admin")
public class UserRoleService extends ServiceImpl<UserRoleMapper, IUserRole> implements IUserRoleService {

    @Override
    public Set<String> queryByUserId(String userId) {
        QueryWrapper<IUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<IUserRole> userRoleList = list(queryWrapper);
        return userRoleList.stream().map(IUserRole::getRoleId).collect(Collectors.toSet());
    }

    @Override
    public boolean add(IUserRole iUserRole) {
        return this.save(iUserRole);
    }
}
