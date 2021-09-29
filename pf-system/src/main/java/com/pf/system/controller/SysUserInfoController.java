package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.constant.CommonConstants;
import com.pf.system.constants.SystemConstants;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.service.ISysUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @ClassName : SysUserInfoController
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-11:16
 */
@Api(value = "用户controller", tags = {"用户操作接口"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysUserInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysUserInfoController {

    public final static String API_PREFIX = "/sysUserInfo";

    @Resource(name = "sysUserInfoService")
    private ISysUserInfoService iSysUserInfoService;

    @ApiOperation(value="用户注册", notes="用户注册")
    @PostMapping(value = "/registerGuest")
    public CommonResult<Object> registerGuest(@RequestBody SysUserInfo sysUserInfo) {
        return iSysUserInfoService.registerGuest(sysUserInfo);
    }

    @ApiOperation(value="用户注册", notes="用户注册")
    @PostMapping(value = "/adminCreate")
    public CommonResult<Object> adminCreate(@RequestBody SysUserInfo sysUserInfo) {
        return iSysUserInfoService.adminCreate(sysUserInfo);
    }
}
