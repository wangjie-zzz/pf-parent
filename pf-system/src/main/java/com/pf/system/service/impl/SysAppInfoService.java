package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
import com.pf.enums.UseStateEnum;
import com.pf.system.dao.SysAppInfoMapper;
import com.pf.system.model.entity.SysAppInfo;
import com.pf.system.model.entity.SysMenuInfo;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.service.ISysAppInfoService;
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
 */
@Service
public class SysAppInfoService implements ISysAppInfoService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysAppInfoMapper sysAppInfoMapper;

    /**
    * @Title: 查询应用+下属菜单信息（未加权限）
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/11/3 21:02
    * @return:
    * @throws:
    */
    @Override
    @Transactional(readOnly = true)
    public CommonResult<List<SysAppInfo>> selectAppAndMenuList() {
        SysUserInfo sysUserInfo = CacheDataUtil.getUserCacheBean(redisTemplate);
        if(sysUserInfo == null) {
            Asserts.fail(SysStatusCode.UNAUTHORIZED);
        }
        List<SysAppInfo> appInfos = sysAppInfoMapper.selectListWithMenus();
        appInfos.forEach(e -> {
            e.setAppActiveRule("/".concat(e.getAppId()));
            if(!CollectionUtils.isEmpty(e.getSysMenuInfos())) {
                List<SysMenuInfo> roots = new ArrayList<>();
                Iterator<SysMenuInfo> it = e.getSysMenuInfos().iterator();
                while (it.hasNext()) {
                    SysMenuInfo menuInfo = it.next();
                    if(menuInfo.getMenuId().equals(menuInfo.getMenuSupMenuId())) {
                        roots.add(menuInfo);
                        it.remove();
                    }
                }
                for (SysMenuInfo child : e.getSysMenuInfos()) {
                    child.addChildren(roots);
                }
                e.setSysMenuInfos(roots);
            }
        });
        return CommonResult.success(appInfos);
    }

    @Override
    @Transactional(readOnly = true)
    public CommonResult<List<SysAppInfo>> selectAppList() {
        SysUserInfo sysUserInfo = CacheDataUtil.getUserCacheBean(redisTemplate);
        if(sysUserInfo == null) {
            Asserts.fail(SysStatusCode.UNAUTHORIZED);
        }
        List<SysAppInfo> list = sysAppInfoMapper.selectList(Wrappers.emptyWrapper());
        return CommonResult.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> addApp(SysAppInfo sysAppInfo) {
        SysUserInfo sysUserInfo = CacheDataUtil.getUserCacheBean(redisTemplate);
        if(sysUserInfo == null) {
            Asserts.fail(SysStatusCode.UNAUTHORIZED);
        }
        sysAppInfo.setAppState(UseStateEnum.EFFECTIVE.getCodeToStr());
        sysAppInfo.setAppIntDate(LocalDateTime.now());
        sysAppInfo.setAppIntUser(sysUserInfo.getUserId());
        sysAppInfo.setAppUpdDate(LocalDateTime.now());
        sysAppInfo.setAppUpdUser(sysUserInfo.getUserId());
        sysAppInfo.setAppUseState(UseStateEnum.EFFECTIVE.getCode());
        sysAppInfoMapper.insert(sysAppInfo);
        return CommonResult.success();
    }

}
