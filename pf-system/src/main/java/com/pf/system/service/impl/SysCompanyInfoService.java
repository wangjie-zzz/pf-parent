package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.aop.context.UserContext;
import com.pf.enums.UseStateEnum;
import com.pf.system.dao.SysCompanyInfoMapper;
import com.pf.system.dao.SysDeptInfoMapper;
import com.pf.system.dao.SysUserInfoMapper;
import com.pf.model.UserDto;
import com.pf.system.model.entity.SysCompanyInfo;
import com.pf.system.model.entity.SysDeptInfo;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.service.ISysCompanyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
public class SysCompanyInfoService implements ISysCompanyInfoService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysCompanyInfoMapper sysCompanyInfoMapper;
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;
    @Autowired
    private SysDeptInfoMapper sysDeptInfoMapper;


    @Override
    @Transactional(readOnly = true)
    public CommonResult<List<SysUserInfo>> userList(Long id , boolean isCom) {
        List<SysUserInfo> list;
        if(isCom) {
            list = sysUserInfoMapper.selectList(Wrappers.lambdaQuery(SysUserInfo.class).eq(SysUserInfo::getUserComId, id));
        } else {
            list = sysUserInfoMapper.selectByDeptId(id);
        }
        list.forEach(user -> user.setUserPasswd(""));
        return CommonResult.success(list);
    }
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
        UserContext.getSysUserHolder(true);
        List<SysCompanyInfo> list = sysCompanyInfoMapper.selectListWithDept();
        return CommonResult.success(list);
    }
    @Override
    @Transactional(readOnly = true)
    public CommonResult<List<SysDeptInfo>> deptList(Long id, boolean isCom) {
        LambdaQueryWrapper<SysDeptInfo> wrapper = Wrappers.lambdaQuery(SysDeptInfo.class);
        if(isCom) {
            wrapper.eq(SysDeptInfo::getDeptComId, id);
        } else {
            wrapper.eq(SysDeptInfo::getDeptSupDeptId, id);
        }
        return CommonResult.success(sysDeptInfoMapper.selectList(wrapper));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> addCompany(SysCompanyInfo sysCompanyInfo) {
        UserDto sysUserInfo = UserContext.getSysUserHolder(true);
        sysCompanyInfo.setComUpdDate(LocalDateTime.now());
        sysCompanyInfo.setComUpdUser(sysUserInfo.getUserId());
        if(StringUtils.isEmpty(sysCompanyInfo.getComId())) {
            /*insert*/
            sysCompanyInfo.setComId(SnowflakeIdWorker.getInstance().nextId());
            if(StringUtils.isEmpty(sysCompanyInfo.getComSupComId())){ /*无父级公司*/
                sysCompanyInfo.setComSupComId(sysCompanyInfo.getComId());
            }
            sysCompanyInfo.setComUseState(UseStateEnum.EFFECTIVE.getCode());/*新增的公司数据都是有效的*/
            sysCompanyInfo.setComIntDate(LocalDateTime.now());
            sysCompanyInfo.setComIntUser(sysUserInfo.getUserId());
            sysCompanyInfoMapper.insert(sysCompanyInfo);
        } else {
            /*update*/
            sysCompanyInfoMapper.updateById(sysCompanyInfo);
        }
        return CommonResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> addDept(SysDeptInfo sysDeptInfo) {
        UserDto sysUserInfo = UserContext.getSysUserHolder(true);
        sysDeptInfo.setDeptUpdDate(LocalDateTime.now());
        sysDeptInfo.setDeptUpdUser(sysUserInfo.getUserId());
        if(StringUtils.isEmpty(sysDeptInfo.getDeptId())) {
            /*insert*/
            sysDeptInfo.setDeptId(SnowflakeIdWorker.getNextId());
            if(StringUtils.isEmpty(sysDeptInfo.getDeptSupDeptId())){ /*无父级公司*/
                sysDeptInfo.setDeptSupDeptId(sysDeptInfo.getDeptId());
            }
            sysDeptInfo.setDeptUseState(UseStateEnum.EFFECTIVE.getCode());/*新增的公司数据都是有效的*/
            sysDeptInfo.setDeptIntDate(LocalDateTime.now());
            sysDeptInfo.setDeptIntUser(sysUserInfo.getUserId());
            sysDeptInfoMapper.insert(sysDeptInfo);
        } else {
            /*update*/
            sysDeptInfoMapper.updateById(sysDeptInfo);
        }
        return CommonResult.success();
    }

}
