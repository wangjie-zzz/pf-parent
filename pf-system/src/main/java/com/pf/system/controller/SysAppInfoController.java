package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.system.constants.SystemGeneralConsts;
import com.pf.system.model.entity.SysAppInfo;
import com.pf.system.service.ISysAppInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName : SysUserInfoController
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/9/17-11:16
 */
@Api(value = "用户controller", tags = {"用户操作接口"})
@RestController
@RequestMapping(value = SystemGeneralConsts.MS_API_PREFIX + SysAppInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysAppInfoController {

    public final static String API_PREFIX = "/sysAppInfo";

    @Resource(name = "sysAppInfoService")
    private ISysAppInfoService iSysAppInfoService;
    
    /**
    * @Title: 用户App查询
    * @Param: 
    * @description: 
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return: 
    * @throws: 
    */
    @ApiOperation(value="App查询", notes="用户App查询")
    @GetMapping(value = "/selectAppAndMenuList", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysAppInfo>> selectAppAndMenuList() {
        return iSysAppInfoService.selectAppAndMenuList();
    }
}
