package com.red.dust.billowing.service.impl;

import com.red.dust.billowing.entity.IRole;

import java.util.Set;

public interface IRoleService {

    /**
     * 根据用户id查询用户拥有的角色
     *
     * @param userId
     * @return
     */
    Set<IRole> query(String userId);

    /**
     * 新增权限
     * @param role
     * @return
     */
    boolean add(IRole role);
}
