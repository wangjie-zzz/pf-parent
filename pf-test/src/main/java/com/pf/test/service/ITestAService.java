package com.pf.test.service;

import com.pf.base.CommonResult;
import com.pf.system.model.UserDto;

/**
 * @ClassName : ITestAService
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/11/5-15:04
 */
public interface ITestAService {

    public CommonResult<UserDto> testA1();

    public String testA1p(String p1);

    public String testA1(String p1);
}
