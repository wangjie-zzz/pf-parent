package com.pf.system.service;

import com.pf.base.CommonResult;
import com.pf.system.model.entity.SysDictInfo;

import java.util.List;

/**
 * @ClassName : ISysDictInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:10
 */
public interface ISysDictInfoService {

    CommonResult<List<SysDictInfo>> list();

    CommonResult<Object> update(SysDictInfo sysDictInfo);

    CommonResult<Object> delete(List<String> ids);
}
