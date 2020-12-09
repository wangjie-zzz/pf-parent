package com.pf.system.service.impl;

import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.system.service.ITestSeataService;
import com.pf.test.model.TestADto;
import com.pf.test.service.ITestAProvider;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @ClassName : TestSeataService
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/11/7-9:41
 */
@Service
@Slf4j
public class TestSeataService implements ITestSeataService {

    @DubboReference
    private ITestAProvider testAProvider;

    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
    public CommonResult<String> insertData(TestADto testADto) {
        testADto.setTest1(SnowflakeIdWorker.getInstance().nextIdString());
        testAProvider.insertData(testADto);
        testADto.setTest1(SnowflakeIdWorker.getInstance().nextIdString());
        testAProvider.insertData(testADto);
//        testADto.setTest1(null); // 报错 回滚上面两条新增数据
//        testAProvider.insertData(testADto);
        CommonResult<String> res = testAProvider.selectData(testADto);
        log.info("查询结果{}", res.getData());
        return CommonResult.success();
    }
}
