package com.pf.system.service.impl;

import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.aop.context.UserContext;
import com.pf.enums.dicts.BoolEnum;
import com.pf.enums.dicts.UseStateEnum;
import com.pf.enums.dicts.UserDataSourceEnum;
import com.pf.system.constants.SystemConstants;
import com.pf.system.dao.SysUdeptRelMapper;
import com.pf.system.dao.SysUserInfoMapper;
import com.pf.model.UserDto;
import com.pf.system.model.entity.SysUdeptRel;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.service.ISysUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * @ClassName : SysUsreInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:19
 */
@Service
public class SysUserInfoService implements ISysUserInfoService {

    @Autowired
    private RestTemplate loadBalancedRestTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysUserInfoMapper sysUserInfoMapper;
    @Autowired
    private SysUdeptRelMapper sysUdeptRelMapper;

    /**
    * @Title: 用户注册
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:38
    * @return:
    * @throws:
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> registerGuest(SysUserInfo sysUserInfo) {
        Long userId = SnowflakeIdWorker.getNextId();
        sysUserInfo.setUserId(userId);
        sysUserInfo.setUserCode(sysUserInfo.getUserPhone());
        sysUserInfo.setUserIntUser(userId);
        sysUserInfo.setUserUpdUser(userId);
        sysUserInfo.setUserUpdDate(LocalDateTime.now());
        sysUserInfo.setUserIntDate(LocalDateTime.now());
        /*TODO 待添加默认公司部门等数据, sysUDeptRel待插入*/
        sysUserInfo.setUserPasswd(SystemConstants.DEFAULT_PWD);
        sysUserInfo.setUserUseState(UseStateEnum.EFFECTIVE.getCode());
        sysUserInfo.setUserDataSource(UserDataSourceEnum.WEB_REGISTER.getCode());
        sysUserInfoMapper.insert(sysUserInfo);
        return CommonResult.success();
    }
    /**
    * @Title: 用户注册
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:38
    * @return:
    * @throws:
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<Object> adminCreate(SysUserInfo sysUserInfo) {
        UserDto authUser = UserContext.getSysUserHolder(true);
        sysUserInfo.setUserId(SnowflakeIdWorker.getNextId());
        sysUserInfo.setUserCode(sysUserInfo.getUserPhone());
        sysUserInfo.setUserPasswd(SystemConstants.DEFAULT_PWD);
        sysUserInfo.setUserIntUser(authUser.getUserId());
        sysUserInfo.setUserUpdUser(authUser.getUserId());
        sysUserInfo.setUserUpdDate(LocalDateTime.now());
        sysUserInfo.setUserIntDate(LocalDateTime.now());
        sysUserInfo.setUserUseState(UseStateEnum.EFFECTIVE.getCode());
        sysUserInfo.setUserDataSource(UserDataSourceEnum.ADMIN_CREATE.getCode());
        sysUserInfoMapper.insert(sysUserInfo);
        SysUdeptRel sysUdeptRel = new SysUdeptRel(sysUserInfo.getUserId(), sysUserInfo.getUserDeptId(), BoolEnum.TRUE.getCode());
        sysUdeptRelMapper.insert(sysUdeptRel);
        return CommonResult.success();
    }

}
