package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.system.constants.SystemConstants;
import com.pf.system.model.entity.*;
import com.pf.system.model.entity.SysRoleInfo;
import com.pf.system.service.ISysRoleInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "角色", tags = {"角色"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysRoleInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysRoleInfoController {

    public final static String API_PREFIX = "/sysRoleInfo";

    @Resource(name = "sysRoleInfoService")
    private ISysRoleInfoService iSysRoleInfoService;

    @ApiOperation(value="角色查询", notes="角色")
    @GetMapping(value = "/list", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysRoleInfo>> list() {
        return iSysRoleInfoService.list();
    }

    @ApiOperation(value="角色新增", notes="角色新增")
    @PostMapping(value = "/add")
    public CommonResult<Object> add(@RequestBody SysRoleInfo sysRoleInfo) {
        return iSysRoleInfoService.add(sysRoleInfo);
    }
    @ApiOperation(value="角色关系新增", notes="角色关系新增")
    @PostMapping(value = "/addRoleRel")
    public CommonResult<Object> addRoleRel(@RequestBody List<SysRoleRel> sysRoleInfos) {
        return iSysRoleInfoService.addRoleRel(sysRoleInfos);
    }
    @ApiOperation(value="角色权限新增", notes="角色权限新增")
    @PostMapping(value = "/addRoleAuth")
    public CommonResult<Object> addRoleAuth(@RequestBody List<SysRoleAuth> sysRoleInfos) {
        return iSysRoleInfoService.addRoleAuth(sysRoleInfos);
    }
}
