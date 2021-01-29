package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
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
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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
            e.setAppActiveRule("/".concat(e.getAppName()));
            if(!CollectionUtils.isEmpty(e.getSysMenuInfoList())) {
                List<SysMenuInfo> root = new ArrayList<>();
                List<SysMenuInfo> children = new ArrayList<>();
                for (SysMenuInfo menuInfo : e.getSysMenuInfoList()) {
                    if(!menuInfo.getMenuSupMenuId().equals(menuInfo.getMenuId())) {
                        children.add(menuInfo);
                    } else {
                        root.add(menuInfo);
                    }
                }
                for (SysMenuInfo child : children) {
                    addChildren(root, child);
                }
                e.setSysMenuInfoList(root);
            }
        });
        return CommonResult.success(appInfos);
    }

    /**
    * @Title: 追加子菜单
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/11/3 21:01
    * @return:
    * @throws:
    */
    private void addChildren(List<SysMenuInfo> roots, SysMenuInfo sysMenuInfo) {
        for (SysMenuInfo root : roots) {
            if(root.getMenuId().equals(sysMenuInfo.getMenuSupMenuId())) {
                if(root.getChildren() == null)
                    root.setChildren(new ArrayList<>());
                root.getChildren().add(sysMenuInfo);
                return;
            } else if(root.getChildren() != null && root.getChildren().size() > 0) {
                addChildren(root.getChildren(), sysMenuInfo);
            }
        }
    }
}
