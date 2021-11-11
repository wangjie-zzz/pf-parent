package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.enums.dicts.UseStateEnum;
import com.pf.system.dao.SysTableFieldMapper;
import com.pf.system.dao.SysTableInfoMapper;
import com.pf.system.dao.SysTableFieldMapper;
import com.pf.system.dao.SysTableInfoMapper;
import com.pf.system.model.entity.SysTableField;
import com.pf.system.model.entity.SysTableInfo;
import com.pf.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName : SysTableInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2021/10/14-21:34
 */
@Service
public class SysTableInfoService {

    @Autowired
    private SysTableInfoMapper sysTableInfoMapper;
    @Autowired
    private SysTableFieldMapper sysTableFieldMapper;    
    @Transactional
    public int create(SysTableInfo sysTableInfo) {
        if(sysTableInfoMapper.selectOne(Wrappers.lambdaQuery(SysTableInfo.class)
                .eq(SysTableInfo::getName, sysTableInfo.getName())
                .eq(SysTableInfo::getUseState, UseStateEnum.EFFECTIVE.getCode())) == null
        ) {
            return sysTableInfoMapper.insert(sysTableInfo);
        } else {
            Asserts.fail("表格名称重复！");
            return 0;
        }
    }

    @Transactional
    public int createField(List<SysTableField> fieldList) {
        return sysTableFieldMapper.insertBatch(fieldList);
    }

    @Transactional
    public int updateField(SysTableField sysTableField) {
        return sysTableFieldMapper.updateById(sysTableField);
    }
}
