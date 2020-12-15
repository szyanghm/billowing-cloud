package com.red.dust.billowing.service;

import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.red.dust.billowing.entity.IRole;
import com.red.dust.billowing.mapper.RoleMapper;
import com.red.dust.billowing.service.impl.IRoleService;
import com.red.dust.billowing.service.impl.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@DS("admin")
public class RoleService extends ServiceImpl<RoleMapper, IRole> implements IRoleService {

    @Autowired
    private IUserRoleService iUserRoleService;

    @Override
    @Cached(name="role4user::",key = "#userId",cacheType = CacheType.BOTH)
    public Set<IRole> query(String userId) {
        List<IRole> list = (List<IRole>)this.listByIds(iUserRoleService.queryByUserId(userId));
        return new HashSet<>(list);
    }

    @Override
    public boolean add(IRole role) {
        return this.save(role);
    }
}
