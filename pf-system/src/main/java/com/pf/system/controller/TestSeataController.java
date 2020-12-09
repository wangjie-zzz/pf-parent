package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.system.constants.SystemGeneralConsts;
import com.pf.system.model.entity.SysAppInfo;
import com.pf.system.service.ISysAppInfoService;
import com.pf.system.service.ITestSeataService;
import com.pf.test.model.TestADto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName : SysUserInfoController
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/9/17-11:16
 */
@Api(value = "test-seata", tags = {"test-seata"})
@RestController
@RequestMapping(value = "/test-seata", produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class TestSeataController {

    @Autowired
    private ITestSeataService iTestSeataService;

    @PostMapping(value = "/insertData")
    public CommonResult<String> insertData(TestADto testADto) {
        return iTestSeataService.insertData(testADto);
    }
}
