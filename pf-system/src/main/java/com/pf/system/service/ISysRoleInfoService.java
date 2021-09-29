package com.pf.system.service;

import com.pf.base.CommonResult;
import com.pf.system.model.entity.*;

import java.util.List;

/**
 * @ClassName : SysUserInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:10
 */
public interface ISysRoleInfoService {

    CommonResult<List<SysRoleInfo>> list();
    CommonResult<Object> add(SysRoleInfo sysMenuInfo);
    CommonResult<Object> addRoleRel(List<SysRoleRel> sysRoleRels);
    CommonResult<Object> addRoleAuth(List<SysRoleAuth> sysRoleAuths);
}
