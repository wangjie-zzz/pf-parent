package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.aop.context.UserContext;
import com.pf.system.dao.SysTenantInfoMapper;
import com.pf.model.UserDto;
import com.pf.system.model.entity.SysTenantInfo;
import com.pf.system.service.ISysTenantInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName : SysUsreInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:19
 */
@Service
public class SysTenantInfoService implements ISysTenantInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysTenantInfoMapper sysTenantInfoMapper;

    @Override
    public CommonResult<List<SysTenantInfo>> list() {
        return CommonResult.success(sysTenantInfoMapper.selectList(Wrappers.emptyWrapper()));
    }

    @Override
    public CommonResult<Object> update(SysTenantInfo sysTenantInfo) {
        UserDto sysUserInfo = UserContext.getSysUserHolder(true);
        sysTenantInfo.setTenUpdDate(LocalDateTime.now());
        sysTenantInfo.setTenUpdUser(sysUserInfo.getUserId());
        if(StringUtils.isEmpty(sysTenantInfo.getTenId())) {
            sysTenantInfo.setTenId(SnowflakeIdWorker.getNextId());
            sysTenantInfo.setTenIntDate(LocalDateTime.now());
            sysTenantInfo.setTenIntUser(sysUserInfo.getUserId());
            sysTenantInfoMapper.insert(sysTenantInfo);
        }else {
            sysTenantInfoMapper.updateById(sysTenantInfo);
        }
        return CommonResult.success();
    }

    @Override
    public CommonResult<Object> delete(List<String> ids) {
        sysTenantInfoMapper.deleteBatchIds(ids);
        return CommonResult.success();
    }
}
