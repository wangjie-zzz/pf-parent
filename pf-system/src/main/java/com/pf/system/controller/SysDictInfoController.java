package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.system.constants.SystemConstants;
import com.pf.system.model.entity.SysCompanyInfo;
import com.pf.system.model.entity.SysDeptInfo;
import com.pf.system.model.entity.SysDictInfo;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.service.ISysCompanyInfoService;
import com.pf.system.service.ISysDictInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName : SysCompanyInfoController
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-11:16
 */
@Api(value = "系统字典controller", tags = {"系统字典操作接口"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysDictInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysDictInfoController {

    public final static String API_PREFIX = "/sysDictInfo";

    @Autowired
    private ISysDictInfoService iSysDictInfoService;

    @ApiOperation(value="查询所有", notes="查询所有")
    @GetMapping(value = "/list", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysDictInfo>> list() {
        return iSysDictInfoService.list();
    }
    @ApiOperation(value="删除", notes="删除")
    @PostMapping(value = "/delete")
    public CommonResult<Object> delete(@RequestBody List<String> ids) {
        return iSysDictInfoService.delete(ids);
    }
    @ApiOperation(value="新增/删除", notes="新增/删除")
    @PostMapping(value = "/update")
    public CommonResult<Object> update(@RequestBody SysDictInfo sysDictInfo) {
        return iSysDictInfoService.update(sysDictInfo);
    }
}
