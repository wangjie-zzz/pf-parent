package com.pf.system.service;

import com.pf.base.CommonResult;
import com.pf.test.model.TestADto;
import com.pf.test.service.ITestAProvider;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName : ITestSeataService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/11/7-9:39
 */
@Service
public interface ITestSeataService {

    public CommonResult<String> insertData(TestADto testADto);
    public CommonResult<String> updateData(TestADto testADto);
}
