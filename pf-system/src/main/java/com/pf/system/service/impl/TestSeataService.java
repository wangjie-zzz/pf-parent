package com.pf.system.service.impl;

import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.system.dao.TestSeataMapper;
import com.pf.system.service.ITestSeataService;
import com.pf.test.model.TestADto;
import com.pf.test.service.ITestAProvider;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private TestSeataMapper testSeataMapper;

    @Override
//    @GlobalTransactional(rollbackFor = Exception.class)
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> insertData(TestADto testADto) {
        testADto.setTest1(SnowflakeIdWorker.getInstance().nextIdString());
        testAProvider.insertData(testADto);
        testADto.setTest1(SnowflakeIdWorker.getInstance().nextIdString());
        testAProvider.insertData(testADto);
        testADto.setTest1(null); // 报错 开启全局事务将回滚上面两条新增数据
        testAProvider.insertData(testADto);
        CommonResult<String> res = testAProvider.selectData(testADto);
        log.info("查询结果{}", res.getData());
        return CommonResult.success();
    }
    @Override
    @GlobalTransactional(rollbackFor = Exception.class)
//    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> updateData(TestADto testADto) {
        testADto.setTest1("update_test");
        testAProvider.insertData(testADto);
        /*本模块修改testA数据(根据主键修改),
        若不开启全局事务，那么此时本地事务应该未提交，将会导致死锁超时:  com.mysql.cj.exceptions.CJCommunicationsException: Communications link failure
        若开启全局事务，那么此时本地事务已经提交，远程调用其它模块将会修改成功，也将会读到最新数据（即seata读隔离的读未提交）
        */
        testADto.setTest2("update_test");
        testSeataMapper.updateTestA(testADto);
        /*远程修改testA数据(根据主键修改)*/
        testADto.setTest2("update_test_1");
        testAProvider.updateData(testADto);
        CommonResult<String> res = testAProvider.selectData(testADto);
        log.info("查询结果{}", res.getData());
        return CommonResult.success();
    }
}
