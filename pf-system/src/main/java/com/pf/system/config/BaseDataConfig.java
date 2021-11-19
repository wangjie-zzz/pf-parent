package com.pf.system.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.enums.dicts.UseStateEnum;
import com.pf.system.component.DictHandler;
import com.pf.system.component.FormHandler;
import com.pf.system.component.TableHandler;
import com.pf.system.dao.*;
import com.pf.system.model.dto.*;
import com.pf.system.model.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName : BaseDataConfig
 * @Description :
 * @Author : wangjie
 * @Date: 2021/11/9-10:26
 */
@Slf4j
@Configuration
public class BaseDataConfig {
    
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SysDictInfoMapper sysDictInfoMapper;
    @Autowired
    private SysFormInfoMapper sysFormInfoMapper;
    @Autowired
    private SysTableInfoMapper sysTableInfoMapper;
    @Autowired
    private SysFormFieldMapper sysFormFieldMapper;
    @Autowired
    private SysTableFieldMapper sysTableFieldMapper;
    
    @Bean
    public DictHandler dictHandler() {
        log.info("初始化字典数据：");
        return new DictHandler(redisTemplate) {
            @Override
            public List<DictInfoDto> init() {
                return sysDictInfoMapper.selectList(Wrappers.lambdaQuery(SysDictInfo.class)
                        .eq(SysDictInfo::getUseState, UseStateEnum.EFFECTIVE.getCode())
                ).stream().map(dict -> DictInfoDto.buildBy(dict, DictInfoDto.class)).collect(Collectors.toList());
            }
        };
    }
    @Bean
    public FormHandler formHandler() {
        log.info("初始化表单数据：");

        return new FormHandler(redisTemplate) {
            @Override
            public List<FormInfoDto> init() {
                return getFormInDb(null);
            }

            @Override
            public List<FormInfoDto> getByNamesInDb(List<String> formNameEnums) {
                return getFormInDb(formNameEnums);
            }
        };
    }
    private List<FormInfoDto> getFormInDb(List<String> enums) {
        LambdaQueryWrapper<SysFormInfo> formWrapper = Wrappers.lambdaQuery(SysFormInfo.class)
                .eq(SysFormInfo::getUseState, UseStateEnum.EFFECTIVE.getCode());
        if(!CollectionUtils.isEmpty(enums)) {
            formWrapper.in(SysFormInfo::getName, enums);
        }
        List<FormInfoDto> formInfoDtos = sysFormInfoMapper.selectList(formWrapper)
                .stream().map(form -> FormInfoDto.buildBy(form, FormInfoDto.class)).collect(Collectors.toList());
        Map<Long, List<FormFieldDto>> fieldMap =
                sysFormFieldMapper.selectList(Wrappers.lambdaQuery(SysFormField.class)
                        .eq(SysFormField::getUseState, UseStateEnum.EFFECTIVE.getCode())
                ).stream()
                        .map(sysFormField -> FormFieldDto.buildBy(sysFormField, FormFieldDto.class))
                        .collect(Collectors.groupingBy(FormFieldDto::getFormId));
        formInfoDtos.forEach(formInfoDto -> {
            formInfoDto.setFieldDtos(fieldMap.computeIfAbsent(formInfoDto.getFormId(), k -> new ArrayList<>()));
        });
        return formInfoDtos;
    }
    
    @Bean
    public TableHandler tableHandler() {
        log.info("初始化表格数据：");

        return new TableHandler(redisTemplate) {
            @Override
            public List<TableInfoDto> init() {
                return getTableInDb(null);
            }

            @Override
            public List<TableInfoDto> getByNamesInDb(List<String> tableNames) {
                return getTableInDb(tableNames);
            }
        };
    }

    private List<TableInfoDto> getTableInDb(List<String> tableNames) {
        LambdaQueryWrapper<SysTableInfo> tableWrapper = Wrappers.lambdaQuery(SysTableInfo.class)
                .eq(SysTableInfo::getUseState, UseStateEnum.EFFECTIVE.getCode());
        if(!CollectionUtils.isEmpty(tableNames)) {
            tableWrapper.in(SysTableInfo::getName, tableNames);
        }
        List<TableInfoDto> tableInfoDtos = sysTableInfoMapper.selectList(tableWrapper)
                .stream().map(tableInfo -> TableInfoDto.buildBy(tableInfo, TableInfoDto.class)).collect(Collectors.toList());
        Map<Long, List<TableFieldDto>> fieldMap =
                sysTableFieldMapper.selectList(Wrappers.lambdaQuery(SysTableField.class)
                        .eq(SysTableField::getUseState, UseStateEnum.EFFECTIVE.getCode())
                ).stream()
                        .map(sysTableField -> TableFieldDto.buildBy(sysTableField, TableFieldDto.class))
                        .collect(Collectors.groupingBy(TableFieldDto::getTableId));
        tableInfoDtos.forEach(tableInfoDto -> {
            tableInfoDto.setFieldDtos(fieldMap.computeIfAbsent(tableInfoDto.getTableId(), k -> new ArrayList<>()));
        });
        return tableInfoDtos;
    }
}
