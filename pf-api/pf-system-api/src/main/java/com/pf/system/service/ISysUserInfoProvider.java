package com.pf.system.service;
import com.pf.base.CommonResult;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.io.Serializable;

/**
 * @ClassName : ISysUserInfoService
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/9/21-11:48
 */
public interface ISysUserInfoProvider extends Serializable {

    /**
     * @Title: 查询用户及下属角色信息
     * @Param:
     * @description:
     * @author: wangjie
     * @date: 2020/9/17 10:18
     * @return:
     * @throws:
     */
    public CommonResult<String> selectUserAndRoleInfo(String userId);

}
