package com.pf.system.provider;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.pf.util.Asserts;
import com.pf.system.dao.SysRoleInfoMapper;
import com.pf.system.service.ISysUserInfoProvider;
import com.pf.base.CommonResult;
import com.pf.system.dao.SysUserInfoMapper;
import com.pf.system.model.entity.SysRoleInfo;
import com.pf.system.model.entity.SysUpostRel;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.util.JacksonsUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName : SysUserInfoProvider
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/9/21-18:43
 */
@DubboService
public class SysUserInfoProvider implements ISysUserInfoProvider {

    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;
    @Autowired
    private SysRoleInfoMapper sysRoleInfoMapper;

    /**
     * @Title: 查询用户及下属角色信息
     * @Param:
     * @description:
     * @author: wangjie
     * @date: 2020/9/17 10:19
     * @return:
     * @throws:
     */
    @Override
    @Transactional(readOnly = true)
    public CommonResult<String> selectUserAndRoleInfo(String userId) {
        SysUserInfo sysUserInfo = sysUserInfoMapper.selectById(userId);
//        long l = 200000;
//        try {
//            Thread.sleep(l);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        if(sysUserInfo == null) Asserts.fail("该用户不存在！");
        sysUserInfo.checkUserUseState();
        List<String> ids = new ArrayList<>();
        List<SysUpostRel> upostRels = sysUserInfo.getUposts();
        if(!CollectionUtils.isEmpty(upostRels)) {
            ids = upostRels.stream().map(SysUpostRel::getPostId).collect(Collectors.toList());
            ids.add(upostRels.get(0).getUserId());
        } else {
            ids.add(sysUserInfo.getUserId());
        }
        List<SysRoleInfo> roles = sysRoleInfoMapper.selectRoleByUserAndPost(ids);
        sysUserInfo.setRoles(roles);
        return CommonResult.success(JacksonsUtils.writeValueAsString(sysUserInfo));
    }
}
