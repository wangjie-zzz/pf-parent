package com.pf.system.service.impl;

import com.pf.base.CommonResult;
import com.pf.enums.SysStatusCode;
import com.pf.system.dao.SysCompanyInfoMapper;
import com.pf.system.dao.SysUserInfoMapper;
import com.pf.system.model.entity.SysCompanyInfo;
import com.pf.system.model.entity.SysDeptInfo;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.service.ISysCompanyInfoService;
import com.pf.util.Asserts;
import com.pf.util.CacheDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
public class SysCompanyInfoService implements ISysCompanyInfoService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysCompanyInfoMapper sysCompanyInfoMapper;
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;

    /**
    * @Title: 查询组织架构树
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/11/3 21:02
    * @return:
    * @throws:
    */
    @Override
    @Transactional(readOnly = true)
    public CommonResult<List<SysCompanyInfo>> selectComTree() {
        SysUserInfo sysUserInfo = CacheDataUtil.getUserCacheBean(redisTemplate);
        if(sysUserInfo == null) {
            Asserts.fail(SysStatusCode.UNAUTHORIZED);
        }
        List<SysCompanyInfo> list = sysCompanyInfoMapper.selectListWithDept();
        if(CollectionUtils.isEmpty(list))
            return CommonResult.success(list);
        List<SysCompanyInfo> roots = new ArrayList<>();
        Iterator<SysCompanyInfo> it = list.iterator();
        while (it.hasNext()){
            SysCompanyInfo sysCompanyInfo = it.next();
            /*处理公司下部门数据*/
            if(!CollectionUtils.isEmpty(sysCompanyInfo.getSysDeptInfos())){
                Iterator<SysDeptInfo> it1 = sysCompanyInfo.getSysDeptInfos().iterator();
                List<SysDeptInfo> roots1 = new ArrayList<>();
                while (it1.hasNext()){
                    SysDeptInfo sysDeptInfo = it1.next();
                    if(sysDeptInfo.getDeptId().equals(sysDeptInfo.getDeptSupDeptId())){
                        roots1.add(sysDeptInfo);
                        it1.remove();
                    }
                }
                for (SysDeptInfo sysDeptInfo : sysCompanyInfo.getSysDeptInfos()) {
                    sysDeptInfo.addChildren(roots1);
                }
                sysCompanyInfo.setSysDeptInfos(roots1);
            }
            /*获取公司组织根节点*/
            if(sysCompanyInfo.getComId().equals(sysCompanyInfo.getComSupComId())){
                roots.add(sysCompanyInfo);
                it.remove();
            }
        }
        for (SysCompanyInfo sysCompanyInfo : list) {
            sysCompanyInfo.addChildren(roots);
        }
        return CommonResult.success(roots);
    }

    @Override
    @Transactional(readOnly = true)
    public CommonResult<List<SysUserInfo>> selectUsers(String deptId) {
        List<SysUserInfo> list = sysUserInfoMapper.selectByDeptId(deptId);
        return CommonResult.success(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> addCompany(SysCompanyInfo sysCompanyInfo) {
        /*TODO*/
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> addDept(SysDeptInfo sysDeptInfo) {
        /*TODO*/
        return null;
    }

}
