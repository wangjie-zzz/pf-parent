package com.pf.test.service;

import com.pf.base.CommonResult;
import com.pf.test.model.TestADto;

/**
 * @ClassName : ITestAProvider
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/11/6-20:49
 */
public interface ITestAProvider {

    public CommonResult<String> insertData(TestADto testADto);

    public CommonResult<String> deleteData(TestADto testADto);

    public CommonResult<String> updateData(TestADto testADto);

    public CommonResult<String> selectData(TestADto testADto);
}
