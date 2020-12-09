package com.pf.test.provider;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.test.dao.TestAMapper;
import com.pf.test.model.TestADto;
import com.pf.test.model.entity.TestA;
import com.pf.test.service.ITestAProvider;
import com.pf.util.JacksonsUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @ClassName : TestAProvider
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/11/6-20:47
 */
@DubboService
public class TestAProvider implements ITestAProvider {

    @Autowired
    private TestAMapper testAMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> insertData(TestADto testADto) {
        TestA testA = JacksonsUtils.readValue(JacksonsUtils.writeValueAsString(testADto), TestA.class);
        testAMapper.insert(testA);
        return CommonResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> deleteData(TestADto testADto) {
        String id = testADto.getTest1();
        testAMapper.delete(Wrappers.lambdaQuery(TestA.class).eq(TestA::getTest1, id));
        return CommonResult.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> updateData(TestADto testADto) {
        TestA testA = JacksonsUtils.readValue(JacksonsUtils.writeValueAsString(testADto), TestA.class);
        testAMapper.updateById(testA);
        return CommonResult.success();
    }

    /**
    * @Title:
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/11/7 11:07
    * @return:
    * @throws:
    */

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> selectData(TestADto testADto) {
        TestA testA = null;
        if(StringUtils.isNotBlank(testADto.getTest1())) {
//            readOnly = true, 不支持for update
           testA = testAMapper.fuSelectById(testADto.getTest1());
        }
        return CommonResult.success(JacksonsUtils.writeValueAsString(testA));
    }
}
