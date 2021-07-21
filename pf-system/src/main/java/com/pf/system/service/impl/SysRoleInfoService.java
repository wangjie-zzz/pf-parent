package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.enums.SysStatusCode;
import com.pf.enums.UseStateEnum;
import com.pf.system.dao.SysMenuInfoMapper;
import com.pf.system.dao.SysRoleAuthMapper;
import com.pf.system.dao.SysRoleInfoMapper;
import com.pf.system.dao.SysRoleRelMapper;
import com.pf.system.model.entity.*;
import com.pf.system.service.ISysMenuInfoService;
import com.pf.system.service.ISysRoleInfoService;
import com.pf.util.Asserts;
import com.pf.util.CacheDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName : SysUsreInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:19
 * */
@Service
public class SysRoleInfoService implements ISysRoleInfoService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysRoleInfoMapper sysRoleInfoMapper;
    @Autowired
    private SysRoleRelMapper sysRoleRelMapper;
    @Autowired
    private SysRoleAuthMapper sysRoleAuthMapper;

    @Override
    @Transactional(readOnly = true)
    public CommonResult<List<SysRoleInfo>> list() {
        SysUserInfo sysUserInfo;
        if((sysUserInfo = CacheDataUtil.getUserCacheBean(redisTemplate)) == null) {
            Asserts.fail(SysStatusCode.UNAUTHORIZED);
        }
        List<SysRoleInfo> list = sysRoleInfoMapper.selectList(Wrappers.lambdaQuery(SysRoleInfo.class)
                .eq(SysRoleInfo::getRoleTenId, sysUserInfo.getUserTenId()));
        return CommonResult.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> add(SysRoleInfo sysRoleInfo) {
        SysUserInfo sysUserInfo;
        if((sysUserInfo = CacheDataUtil.getUserCacheBean(redisTemplate)) == null) {
            Asserts.fail(SysStatusCode.UNAUTHORIZED);
        }
        sysRoleInfo.setRoleId(SnowflakeIdWorker.getInstance().nextIdString());
        sysRoleInfo.setRoleTenId(sysUserInfo.getUserTenId());
        sysRoleInfo.setRoleType("-");
        sysRoleInfo.setRoleUseState(UseStateEnum.EFFECTIVE.getCodeToStr());
        sysRoleInfo.setRoleIntDate(LocalDateTime.now());
        sysRoleInfo.setRoleUpdDate(LocalDateTime.now());
        sysRoleInfo.setRoleIntUser(sysUserInfo.getUserId());
        sysRoleInfo.setRoleUpdUser(sysUserInfo.getUserId());
        sysRoleInfoMapper.insert(sysRoleInfo);
        return CommonResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> addRoleRel(List<SysRoleRel> sysRoleRels) {
        if(!CollectionUtils.isEmpty(sysRoleRels)) {
            sysRoleRels.forEach(rel -> {
                sysRoleRelMapper.insert(rel);
            });
        }
        return CommonResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> addRoleAuth(List<SysRoleAuth> sysRoleAuths) {
        if(!CollectionUtils.isEmpty(sysRoleAuths)) {
            sysRoleAuths.forEach(auth -> {
                sysRoleAuthMapper.insert(auth);
            });
        }
        return CommonResult.success();
    }
}
