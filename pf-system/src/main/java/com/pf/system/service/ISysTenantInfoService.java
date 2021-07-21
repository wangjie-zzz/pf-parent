package com.pf.system.service;

import com.pf.base.CommonResult;
import com.pf.system.model.entity.SysDictInfo;
import com.pf.system.model.entity.SysTenantInfo;

import java.util.List;

/**
 * @ClassName : ISysDictInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:10
 */
public interface ISysTenantInfoService {

    CommonResult<List<SysTenantInfo>> list();

    CommonResult<Object> update(SysTenantInfo sysTenantInfo);

    CommonResult<Object> delete(List<String> ids);
}
