package com.pf.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pf.base.CommonResult;
import com.pf.model.UserDto;
import com.pf.test.model.entity.TestA;

/**
 * @ClassName : ITestAService
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/11/5-15:04
 */
public interface ITestAService extends IService<TestA> {

    public CommonResult<UserDto> testA1();

    public String testA1p(String p1);

    public String testA1(String p1);
}
