package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
import com.pf.enums.UseStateEnum;
import com.pf.system.dao.SysAppInfoMapper;
import com.pf.system.dao.SysMenuInfoMapper;
import com.pf.system.model.entity.SysAppInfo;
import com.pf.system.model.entity.SysMenuInfo;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.service.ISysAppInfoService;
import com.pf.system.service.ISysMenuInfoService;
import com.pf.util.Asserts;
import com.pf.util.CacheDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
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
        return null;
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
