package com.pf.system.service.impl;

import com.pf.base.CommonResult;
import com.pf.system.dao.SysMenuInfoMapper;
import com.pf.system.model.entity.SysMenuInfo;
import com.pf.system.service.ISysMenuInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName : SysUsreInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:19
 * TODO 菜单
 */
@Service
public class SysMenuInfoService implements ISysMenuInfoService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysMenuInfoMapper sysMenuInfoMapper;

    @Override
    @Transactional(readOnly = true)
    public CommonResult<List<SysMenuInfo>> list() {
        return CommonResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> add(SysMenuInfo sysMenuInfo) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> addBatch(List<SysMenuInfo> sysMenuInfos) {
        return null;
    }
}
