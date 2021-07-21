package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.system.constants.SystemConstants;
import com.pf.system.model.entity.*;
import com.pf.system.service.ISysPostInfoService;
import com.pf.system.service.ISysRoleInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "岗位", tags = {"岗位"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysPostInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysPostInfoController {

    public final static String API_PREFIX = "/sysPostInfo";

    @Resource(name = "sysPostInfoService")
    private ISysPostInfoService iSysPostInfoService;

    @ApiOperation(value="岗位查询", notes="岗位")
    @GetMapping(value = "/list", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysPostInfo>> list() {
        return iSysPostInfoService.list();
    }

    @ApiOperation(value="岗位新增", notes="岗位新增")
    @PostMapping(value = "/add")
    public CommonResult<Object> add(@RequestBody SysPostInfo sysPostInfo) {
        return iSysPostInfoService.add(sysPostInfo);
    }
    @ApiOperation(value="岗位关系新增", notes="岗位关系新增")
    @PostMapping(value = "/addUser")
    public CommonResult<Object> addUser(String postId, @RequestBody List<String> userIds) {
        return iSysPostInfoService.addUser(postId, userIds);
    }
    @ApiOperation(value="岗位关系新增", notes="岗位关系新增")
    @GetMapping(value = "/listUser")
    public CommonResult<List<SysUpostRel>> listUser(@RequestParam("postId") String postId) {
        return iSysPostInfoService.listUser(postId);
    }
}
