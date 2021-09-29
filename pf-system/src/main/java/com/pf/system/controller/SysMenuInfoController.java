package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.system.constants.SystemConstants;
import com.pf.system.model.entity.SysAppInfo;
import com.pf.system.model.entity.SysMenuInfo;
import com.pf.system.service.ISysAppInfoService;
import com.pf.system.service.ISysMenuInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Base64;
import java.util.List;

@Api(value = "菜单", tags = {"菜单"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysMenuInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysMenuInfoController {

    public final static String API_PREFIX = "/sysMenuInfo";

    @Resource(name = "sysMenuInfoService")
    private ISysMenuInfoService iSysMenuInfoService;

    @ApiOperation(value="menu查询", notes="menu查询")
    @GetMapping(value = "/list", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysMenuInfo>> list() {
        return iSysMenuInfoService.list();
    }

    @ApiOperation(value="menu新增", notes="menu新增")
    @PostMapping(value = "/add")
    public CommonResult<Object> add(@RequestBody SysMenuInfo sysMenuInfo) {
        return iSysMenuInfoService.add(sysMenuInfo);
    }
    @ApiOperation(value="menu新增", notes="menu新增")
    @PostMapping(value = "/addBatch")
    public CommonResult<Object> addBatch(@RequestBody List<SysMenuInfo> sysMenuInfos) {
        return iSysMenuInfoService.addBatch(sysMenuInfos);
    }
}
