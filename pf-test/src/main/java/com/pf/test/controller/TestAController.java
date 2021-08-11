package com.pf.test.controller;

import com.pf.base.CommonResult;
import com.pf.system.model.UserDto;
import com.pf.test.service.ITestAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName : TestAController
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/11/5-14:53
 */
@RestController
@RequestMapping(path = "/testA", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class TestAController {

    @Autowired
    private ITestAService iTestAService;

    @GetMapping(path = "/A1", consumes = MediaType.ALL_VALUE)
    public CommonResult<UserDto> testA1() {

        return iTestAService.testA1();
    }
    @GetMapping(path = "/A1_p", consumes = MediaType.ALL_VALUE)
    public String testA1p(@RequestParam(name = "p1") String p1) {
        return iTestAService.testA1p(p1);
    }
    @PostMapping(path = "/A1")
    public String testA1(@RequestBody String p1) {
        return iTestAService.testA1(p1);
    }
}
