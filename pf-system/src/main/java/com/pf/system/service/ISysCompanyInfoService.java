package com.pf.system.service;

import com.pf.base.CommonResult;
import com.pf.system.model.entity.SysAppInfo;
import com.pf.system.model.entity.SysCompanyInfo;
import com.pf.system.model.entity.SysDeptInfo;
import com.pf.system.model.entity.SysUserInfo;

import java.util.List;

/**
 * @ClassName : SysUserInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:10
 */
public interface ISysCompanyInfoService {

    CommonResult<List<SysUserInfo>> userList(Long id , boolean isCom);
    CommonResult<List<SysCompanyInfo>> selectComTree();
    CommonResult<List<SysDeptInfo>> deptList(Long id, boolean isCom);
    CommonResult<String> addCompany(SysCompanyInfo sysCompanyInfo);
    CommonResult<String> addDept(SysDeptInfo sysDeptInfo);
}
