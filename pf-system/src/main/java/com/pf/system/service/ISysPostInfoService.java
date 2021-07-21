package com.pf.system.service;

import com.pf.base.CommonResult;
import com.pf.system.model.entity.*;

import java.util.List;

public interface ISysPostInfoService {

    CommonResult<List<SysPostInfo>> list();
    CommonResult<Object> add(SysPostInfo sysPostInfo);
    CommonResult<Object> addUser(String postId, List<String> userIds);

    CommonResult<List<SysUpostRel>> listUser(String postId);
}
