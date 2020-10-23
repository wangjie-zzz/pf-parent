package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
import com.pf.exception.Asserts;
import com.pf.system.dao.SysAppInfoMapper;
import com.pf.system.model.entity.SysAppInfo;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.service.ISysAppInfoService;
import com.pf.util.CacheDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @ClassName : SysUsreInfoService
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/9/17-10:19
 */
@Service
public class SysAppInfoService implements ISysAppInfoService {

    @Autowired
    private RestTemplate loadBalancedRestTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysAppInfoMapper sysAppInfoMapper;

    @Override
    public CommonResult<List<SysAppInfo>> selectAppAndMenuList() {
        SysUserInfo sysUserInfo = CacheDataUtil.getUserCacheBean(redisTemplate);
        if(sysUserInfo == null) {
            Asserts.fail(SysStatusCode.UNAUTHORIZED);
        }
        List<SysAppInfo> appInfos = sysAppInfoMapper.selectList(Wrappers.emptyWrapper());
        appInfos.forEach(e -> {
            e.setAppActiveRule("/".concat(e.getAppName()));
        });
        return CommonResult.success(appInfos);
    }
}
