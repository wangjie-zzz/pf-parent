package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.aop.context.UserContext;
import com.pf.enums.UseStateEnum;
import com.pf.system.dao.*;
import com.pf.model.UserDto;
import com.pf.system.model.entity.*;
import com.pf.system.service.ISysPostInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName : SysUsreInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:19
 * */
@Service
public class SysPostInfoService implements ISysPostInfoService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysPostInfoMapper sysPostInfoMapper;
    @Autowired
    private SysUpostRelMapper sysUpostRelMapper;

    @Override
    @Transactional(readOnly = true)
    public CommonResult<List<SysPostInfo>> list() {
        UserDto sysUserInfo = UserContext.getSysUserHolder(true);
        List<SysPostInfo> list = sysPostInfoMapper.selectList(Wrappers.lambdaQuery(SysPostInfo.class).eq(SysPostInfo::getPostTenId, sysUserInfo.getUserTenId()));
        return CommonResult.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> add(SysPostInfo sysPostInfo) {
        UserDto sysUserInfo = UserContext.getSysUserHolder(true);
        sysPostInfo.setPostId(SnowflakeIdWorker.getNextId());
        sysPostInfo.setPostType(0); // TODO 岗位类型
        sysPostInfo.setPostTenId(sysUserInfo.getUserTenId());
        sysPostInfo.setPostUseState(UseStateEnum.EFFECTIVE.getCode());
        sysPostInfo.setPostUpdDate(LocalDateTime.now());
        sysPostInfo.setPostIntDate(LocalDateTime.now());
        sysPostInfo.setPostIntUser(sysUserInfo.getUserId());
        sysPostInfo.setPostUpdUser(sysUserInfo.getUserId());
        sysPostInfoMapper.insert(sysPostInfo);
        return CommonResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> addUser(Long postId, List<Long> userIds) {
        if(!StringUtils.isEmpty(postId) && !CollectionUtils.isEmpty(userIds)) {
            sysUpostRelMapper.delete(Wrappers.lambdaQuery(SysUpostRel.class).eq(SysUpostRel::getPostId, postId));
            userIds.forEach(rel -> {
                sysUpostRelMapper.insert(new SysUpostRel(rel, postId));
            });
        }
        return CommonResult.success();
    }

    @Override
    @Transactional(readOnly = true)
    public CommonResult<List<SysUpostRel>> listUser(Long postId) {
        List<SysUpostRel> list = sysUpostRelMapper.selectListAndUserName(postId);
        return CommonResult.success(list);
    }
}
