package com.pf.system.service;

import com.pf.base.CommonResult;
import com.pf.system.model.entity.SysAppInfo;

import java.util.List;

/**
 * @ClassName : SysUserInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:10
 */
public interface ISysAppInfoService {

    public CommonResult<List<SysAppInfo>> selectAppAndMenuList();
    public CommonResult<List<SysAppInfo>> selectAppList();
    public CommonResult<String> addApp(SysAppInfo sysAppInfo);
}
