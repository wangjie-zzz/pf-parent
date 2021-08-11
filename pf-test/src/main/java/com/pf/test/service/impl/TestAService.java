package com.pf.test.service.impl;

import com.pf.base.CommonResult;
import com.pf.enums.LoginTypeEnum;
import com.pf.system.model.UserDto;
import com.pf.system.service.ISysUserInfoProvider;
import com.pf.test.service.ITestAService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @ClassName : TestAService
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/11/5-15:04
 */
@Service
public class TestAService implements ITestAService {

    @DubboReference
    private ISysUserInfoProvider iSysUserInfoProvider;

    @Override
    public CommonResult<UserDto> testA1() {
        CommonResult<UserDto> res = iSysUserInfoProvider.selectUserAndRoleInfo(842109738912055297l, LoginTypeEnum.USER_CODE.getCode());
        return res;
    }

    @Override
    public String testA1p(String p1) {
        return "hello im testA1 and ".concat(p1);
    }

    @Override
    public String testA1(String p1) {
        return "hello im testA1 and ".concat(p1);
    }
}
