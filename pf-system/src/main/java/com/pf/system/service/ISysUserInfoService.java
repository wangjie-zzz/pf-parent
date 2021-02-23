package com.pf.system.service;

import com.pf.base.CommonResult;
import com.pf.system.model.request.LoginRequest;
import com.pf.system.model.domain.Token;
import com.pf.system.model.entity.SysUserInfo;

/**
 * @ClassName : SysUserInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:10
 */
public interface ISysUserInfoService {

    /**
    * @Title: 用户注册
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:37
    * @return:
    * @throws:
    */
    public CommonResult<Object> registerGuest(SysUserInfo sysUserInfo);

    /**
    * @Title: 用户登录
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 16:38
    * @return:
    * @throws:
    */
    public CommonResult<Token> login(LoginRequest loginRequest);

    CommonResult<String> refreshToken(String refreshToken);
}
