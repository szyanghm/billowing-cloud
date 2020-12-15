package com.red.dust.billowing.service.impl;

import com.red.dust.billowing.entity.IUserRole;

import java.util.Set;

public interface IUserRoleService {

    /**
     * 根据userId查询用户拥有的角色id集合
     * @param userId
     * @return
     */
    Set<String> queryByUserId(String userId);


    boolean add(IUserRole iUserRole);
}
