package com.red.dust.billowing.service;

import cn.hutool.core.util.ObjectUtil;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.red.dust.billowing.entity.IUser;
import com.red.dust.billowing.entity.IUserRole;
import com.red.dust.billowing.mapper.UserMapper;
import com.red.dust.billowing.service.impl.IUserRoleService;
import com.red.dust.billowing.service.impl.IUserService;
import com.red.dust.billowing.vo.MiniUserVO;
import com.red.dust.billowing.vo.ResultVO;
import com.red.dust.billowing.vo.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@DS("admin")
public class UserService extends ServiceImpl<UserMapper, IUser> implements IUserService {

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Cached(name = "user::", key = "#uniqueId", cacheType = CacheType.BOTH)
    public IUser getByUniqueId(String uniqueId) {
        IUser user = this.getOne(new QueryWrapper<IUser>()
                .eq("username", uniqueId)
                .or()
                .eq("mobile", uniqueId));
//        if (Objects.isNull(user)) {
//            throw new UserNotFoundException("user not found with uniqueId:" + uniqueId);
//        }
        user.setRoleIds(userRoleService.queryByUserId(user.getId()));
        return user;
    }

    /**
     * 新增oauth2用户
     *
     * @param vo
     * @return
     */
    @Transactional
    public ResultVO<String> addUser(MiniUserVO vo) {
        IUser user = new IUser();
        user.setUsername(vo.getOpenId());
        user.setAddr(vo.getAddr());
        user.setName(String.valueOf(System.currentTimeMillis()));
        user.setPassword(passwordEncoder.encode(vo.getOpenId()));
        user.setAccountNonExpired(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);
        user.setLoginTime(new Date());
        user.setEnabled(true);
        IUser iUser = this.getOne(new QueryWrapper<IUser>().eq("username", vo.getOpenId()));
        if (ObjectUtil.isNotNull(iUser)) {
            iUser.setLoginTime(user.getLoginTime());
            iUser.setAddr(user.getAddr());
            if(this.saveOrUpdate(iUser)){
                return ResultVO.success();
            }
            return ResultVO.fail();
        }
        if (this.save(user)){
            IUserRole iUserRole = new IUserRole();
            iUserRole.setUserId(user.getId());
            iUserRole.setRoleId("101");
            if(userRoleService.add(iUserRole)) {
                //插入用户权限
                return ResultVO.success();
            }
        }
        return ResultVO.fail();
    }

    @Override
    public UserInfoVO getByOpenId(String openid) {
        return baseMapper.getByOpenId(openid);
    }
}
