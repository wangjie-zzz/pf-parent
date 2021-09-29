package com.pf.system.service;

import com.pf.base.CommonResult;
import com.pf.system.model.entity.SysAppInfo;
import com.pf.system.model.entity.SysMenuInfo;

import java.util.List;

/**
 * @ClassName : SysUserInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:10
 */
public interface ISysMenuInfoService {

    CommonResult<List<SysMenuInfo>> list();
    CommonResult<Object> add(SysMenuInfo sysMenuInfo);
    CommonResult<Object> addBatch(List<SysMenuInfo> sysMenuInfos);
}
