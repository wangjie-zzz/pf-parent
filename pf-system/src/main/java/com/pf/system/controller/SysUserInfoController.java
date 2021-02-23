package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.constant.SysGeneralConsts;
import com.pf.system.constants.SystemGeneralConsts;
import com.pf.system.model.domain.Token;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.model.request.LoginRequest;
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
@RequestMapping(value = SystemGeneralConsts.MS_API_PREFIX + SysUserInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysUserInfoController {

    public final static String API_PREFIX = "/sysUserInfo";

    @Resource(name = "sysUserInfoService")
    private ISysUserInfoService iSysUserInfoService;

    /**
    * @Title: 用户注册
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="用户注册", notes="用户注册")
    @PostMapping(value = "/registerGuest")
    public CommonResult<Object> registerGuest(@RequestBody SysUserInfo sysUserInfo) {
        return iSysUserInfoService.registerGuest(sysUserInfo);
    }

    /**
    * @Title: 用户登录
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="用户登录", notes="用户登录")
    @PostMapping(value = SysGeneralConsts.PERMIT_ENDPOINT + "/login")
    public CommonResult<Token> login(@RequestBody LoginRequest loginRequest) {
        return iSysUserInfoService.login(loginRequest);
    }
    /**
    * @Title: token刷新
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="token刷新", notes="token刷新")
    @GetMapping(value = SysGeneralConsts.PERMIT_ENDPOINT + "/refreshToken")
    public CommonResult<String> refreshToken(@RequestParam("refreshToken") String refreshToken) {
        return iSysUserInfoService.refreshToken(refreshToken);
    }
}
