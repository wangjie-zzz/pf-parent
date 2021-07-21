package com.pf.system.service;

import com.pf.base.CommonResult;
import com.pf.system.model.request.LoginRequest;
import com.pf.system.model.Token;
import com.pf.system.model.entity.SysUserInfo;

/**
 * @ClassName : SysUserInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:10
 */
public interface ISysUserInfoService {

    CommonResult<Object> registerGuest(SysUserInfo sysUserInfo);
    CommonResult<Object> adminCreate(SysUserInfo sysUserInfo);

    /**
    * @Title: 用户登录
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 16:38
    * @return:
    * @throws:
    */
    CommonResult<Token> login(LoginRequest loginRequest);

    CommonResult<String> refreshToken(String refreshToken);
}
