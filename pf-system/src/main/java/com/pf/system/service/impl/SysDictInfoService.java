package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.aop.context.UserContext;
import com.pf.enums.dicts.UseStateEnum;
import com.pf.system.dao.SysDictInfoMapper;
import com.pf.model.UserDto;
import com.pf.system.model.entity.SysDictInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName : SysUsreInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:19
 */
@Service
public class SysDictInfoService {

    @Autowired
    private SysDictInfoMapper sysDictInfoMapper;

    public List<SysDictInfo> list() {
        return sysDictInfoMapper.selectList(Wrappers.lambdaQuery(SysDictInfo.class).eq(SysDictInfo::getUseState, UseStateEnum.EFFECTIVE.getCode()));
    }

    @Transactional
    public int update(SysDictInfo sysDictInfo) {
        return sysDictInfoMapper.updateById(sysDictInfo);
    }
    @Transactional
    public int insert(SysDictInfo sysDictInfo) {
        return sysDictInfoMapper.insert(sysDictInfo);
    }

    @Transactional
    public int delete(List<String> ids) {
        return sysDictInfoMapper.deleteBatchIds(ids);
    }
}
