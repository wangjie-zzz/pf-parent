package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.system.constants.SystemConstants;
import com.pf.system.model.entity.SysCompanyInfo;
import com.pf.system.model.entity.SysDeptInfo;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.service.ISysCompanyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "组织架构controller", tags = {"组织架构操作接口"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysCompanyInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysCompanyInfoController {

    public final static String API_PREFIX = "/sysCompanyInfo";

    @Resource(name = "sysCompanyInfoService")
    private ISysCompanyInfoService iSysCompanyInfoService;

    /**
    * @Title: 组织架构树查询
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="组织架构树查询", notes="组织架构树查询")
    @GetMapping(value = "/selectComTree", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysCompanyInfo>> selectComTree() {
        return iSysCompanyInfoService.selectComTree();
    }

    /**
    * @Title: 人员查询
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="人员查询", notes="人员查询")
    @GetMapping(value = "/userList", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysUserInfo>> userList(@RequestParam("id") String id, @RequestParam("isCom") boolean isCom) {
        return iSysCompanyInfoService.userList(id, isCom);
    }
    /**
    * @Title: 部门查询
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="部门查询", notes="部门查询")
    @GetMapping(value = "/deptList", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysDeptInfo>> deptList(@RequestParam("id") String id, @RequestParam("isCom") boolean isCom) {
        return iSysCompanyInfoService.deptList(id, isCom);
    }
    /**
    * @Title: 新增公司信息
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="新增公司信息", notes="新增公司信息")
    @PostMapping(value = "/addCompany")
    public CommonResult<String> addCompany(@RequestBody SysCompanyInfo sysCompanyInfo) {
        return iSysCompanyInfoService.addCompany(sysCompanyInfo);
    }
    /**
    * @Title: 新增部门信息
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="新增部门信息", notes="新增部门信息")
    @PostMapping(value = "/addDept")
    public CommonResult<String> addDept(@RequestBody SysDeptInfo sysDeptInfo) {
        return iSysCompanyInfoService.addDept(sysDeptInfo);
    }
}
