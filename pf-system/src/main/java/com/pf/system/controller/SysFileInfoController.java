package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.system.constants.SystemConstants;
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
@Api(value = "系统文件controller", tags = {"文件操作接口"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysFileInfoController.API_PREFIX, produces = {MediaType.MULTIPART_FORM_DATA_VALUE}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
public class SysFileInfoController {

    public final static String API_PREFIX = "/sysFileInfo";

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
    @GetMapping(value = "/selectAppList", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysAppInfo>> selectAppList() {
        return iSysAppInfoService.selectAppList();
    }
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
    @PostMapping(value = "/addApp")
    public CommonResult<String> addApp(@RequestBody SysAppInfo sysAppInfo) {
        return iSysAppInfoService.addApp(sysAppInfo);
    }
}
