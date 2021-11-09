package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.system.constants.SystemConstants;
import com.pf.system.model.entity.SysTenantInfo;
import com.pf.system.service.ISysTenantInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName : SysCompanyInfoController
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-11:16
 */
@Api(value = "租户controller", tags = {"租户操作接口"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysTenantInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysTenantInfoController {

    public final static String API_PREFIX = "/sysTenantInfo";

    @Autowired
    private ISysTenantInfoService iSysTenantInfoService;

    @ApiOperation(value="查询所有", notes="查询所有")
    @GetMapping(value = "/list", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysTenantInfo>> list() {
        return iSysTenantInfoService.list();
    }
    @ApiOperation(value="删除", notes="删除")
    @PostMapping(value = "/delete")
    public CommonResult<Object> delete(@RequestBody List<String> ids) {
        return iSysTenantInfoService.delete(ids);
    }
    @ApiOperation(value="新增/删除", notes="新增/删除")
    @PostMapping(value = "/update")
    public CommonResult<Object> update(@RequestBody SysTenantInfo sysTenantInfo) {
        return iSysTenantInfoService.update(sysTenantInfo);
    }
}
