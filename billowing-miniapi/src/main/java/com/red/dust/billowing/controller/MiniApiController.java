package com.red.dust.billowing.controller;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.hutool.core.util.ObjectUtil;
import com.red.dust.billowing.common.LocalProvider;
import com.red.dust.billowing.common.SysUser;
import com.red.dust.billowing.config.WxMaConfiguration;
import com.red.dust.billowing.consumer.AuthApiConsumer;
import com.red.dust.billowing.consumer.MiniApiConsumer;
import com.red.dust.billowing.entity.IMiniUser;
import com.red.dust.billowing.entity.IUser;
import com.red.dust.billowing.utils.HttpUtils;
import com.red.dust.billowing.utils.RedisUtil;
import com.red.dust.billowing.vo.MiniUserVO;
import com.red.dust.billowing.vo.ResultVO;
import com.red.dust.billowing.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
public class MiniApiController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AuthApiConsumer authApiConsumer;

    @Autowired
    private MiniApiConsumer miniApiConsumer;

    @GetMapping("/login/{appid}")
    public ResultVO login(@PathVariable("appid") String appid, String code, HttpServletRequest request){
        if(StringUtils.isBlank(code)){
            return ResultVO.success();
        }
        final WxMaService wxMaService = WxMaConfiguration.getMaService(appid);
        try {
            //新用户生成token
            WxMaJscode2SessionResult sessionResult = wxMaService.getUserService().getSessionInfo(code);
            log.info(sessionResult.getSessionKey());
            log.info(sessionResult.getOpenid());
            //TODO 执行注册，注册成功之后再执行oauth2登录认证，然后返回token
            MiniUserVO vo = new MiniUserVO();
            vo.setOpenId(sessionResult.getOpenid());
            vo.setAddr(HttpUtils.getIpAddr(request));
            ResultVO<String> resultVO = authApiConsumer.addUser(vo);
            redisUtil.setStr(sessionResult.getOpenid(),sessionResult.getSessionKey());
            if(resultVO.getCode().equals(ResultVO.SUCCESSFUL_CODE)){
                //注册成功,再执行oauth2登录认证，然后返回token
                IUser iUser = new IUser();
                iUser.setUsername(sessionResult.getOpenid());
                iUser.setPassword(sessionResult.getOpenid());
                return authApiConsumer.login(iUser);
            }
            return ResultVO.fail();
        }catch (WxErrorException e){
            log.error(e.getMessage());
        }
        return ResultVO.success();
    }

    @GetMapping("/userInfo/{appid}")
    public ResultVO info(@PathVariable("appid") String appid,
                         String signature,String rawData,String encryptedData,String iv){
        final WxMaService wxMaService = WxMaConfiguration.getMaService(appid);
//        if(!wxMaService.getUserService().checkUserInfo(sessionKey,rawData,signature)){
//            return ResultVO.fail(SystemErrorType.USER_CHECK_FAILED);
//        }
        SysUser sysUser = LocalProvider.getUser();
        String sessionKey = redisUtil.getStr(sysUser.getOpenId());
        MiniUserVO vo = new MiniUserVO();
        if(StringUtils.isBlank(sysUser.getUserId())){
            WxMaUserInfo userInfo = wxMaService.getUserService().getUserInfo(sessionKey,encryptedData,iv);
            BeanUtils.copyProperties(userInfo,vo);
        }
        vo.setOpenId(sysUser.getOpenId());
        return addUser(vo,0);
//        // 解密
//        WxMaPhoneNumberInfo phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(sessionKey, encryptedData, iv);

    }


    public ResultVO<IMiniUser> addUser(MiniUserVO vo,int i){
        ResultVO<IMiniUser> resultVO = miniApiConsumer.addUser(vo);
        if (!resultVO.getCode().equals(ResultVO.SUCCESSFUL_CODE)) {
            i++;
            if(i<3){
                addUser(vo,i);
            }
        }
        return  resultVO;
    }

}
