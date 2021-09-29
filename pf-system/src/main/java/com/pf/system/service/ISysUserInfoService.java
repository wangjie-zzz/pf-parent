package com.pf.system.service;

import com.pf.base.CommonResult;
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

}
