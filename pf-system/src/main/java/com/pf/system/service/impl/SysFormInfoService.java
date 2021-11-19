package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.enums.dicts.UseStateEnum;
import com.pf.system.dao.SysFormFieldMapper;
import com.pf.system.dao.SysFormInfoMapper;
import com.pf.system.model.entity.SysFormField;
import com.pf.system.model.entity.SysFormInfo;
import com.pf.util.Asserts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName : SysFormInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2021/10/14-21:34
 */
@Service
public class SysFormInfoService {
    @Autowired
    private SysFormInfoMapper sysFormInfoMapper;
    @Autowired
    private SysFormFieldMapper sysFormFieldMapper;
    
    @Transactional
    public int createForm(SysFormInfo sysFormInfo) {
        if(sysFormInfoMapper.selectOne(Wrappers.lambdaQuery(SysFormInfo.class)
                .eq(SysFormInfo::getName, sysFormInfo.getName())
                .eq(SysFormInfo::getUseState, UseStateEnum.EFFECTIVE.getCode())) == null
        ) {
            return sysFormInfoMapper.insert(sysFormInfo);
        } else {
            Asserts.fail("表单名称重复！");
            return 0;
        }
    }

    @Transactional
    public int createFormField(List<SysFormField> fieldList) {
        return sysFormFieldMapper.insertBatch(fieldList);
    }

    @Transactional
    public int updateFormField(SysFormField sysFormField) {
        return sysFormFieldMapper.updateById(sysFormField);
    }
}
