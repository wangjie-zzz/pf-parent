package com.pf.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.BaseEntity;
import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.enums.dicts.UseStateEnum;
import com.pf.system.component.FormHandler;
import com.pf.system.component.TableHandler;
import com.pf.system.constants.SystemConstants;
import com.pf.system.dao.SysTableFieldMapper;
import com.pf.system.dao.SysTableInfoMapper;
import com.pf.system.dao.SysTableFieldMapper;
import com.pf.system.dao.SysTableInfoMapper;
import com.pf.system.model.dto.FormInfoDto;
import com.pf.system.model.dto.TableInfoDto;
import com.pf.system.model.entity.SysTableField;
import com.pf.system.model.entity.SysTableInfo;
import com.pf.system.model.vo.SysTableFieldVo;
import com.pf.system.model.vo.SysTableInfoVo;
import com.pf.system.model.vo.TableColumnVo;
import com.pf.system.service.impl.SysTableInfoService;
import com.pf.system.service.impl.SysTableInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName : 表单配置Controller
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-11:16
 */
@Api(value = "表格配置controller", tags = {"表格配置接口"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysTableInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysTableInfoController {

    public final static String API_PREFIX = "/sysTableInfo";

    @Autowired
    private SysTableInfoMapper sysTableInfoMapper;
    @Autowired
    private SysTableFieldMapper sysTableFieldMapper;
    @Autowired
    private SysTableInfoService sysTableInfoService;
    @Autowired
    private TableHandler tableHandler;

    /**
    * @Title: dbs查询
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="cache列表查询", notes="cache列表查询")
    @PostMapping(value = "/cacheList")
    public CommonResult<List<TableInfoDto>> getCacheList(@RequestBody List<String > tableNames) {
        List<TableInfoDto> formInfoDtos = tableHandler.getByNames(tableNames);
        return CommonResult.success(formInfoDtos);
    }
    /**
    * @Title: dbs查询
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="列表查询", notes="列表查询")
    @GetMapping(value = "/list", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysTableInfoVo>> getTableList() {
        return CommonResult.success(SysTableInfoVo.buildsBy(
                sysTableInfoMapper.selectList(Wrappers.lambdaQuery(SysTableInfo.class)
                        .eq(SysTableInfo::getUseState, UseStateEnum.EFFECTIVE.getCode())),
                SysTableInfoVo.class)
        );
    }
    /**
    * @Title: dbs查询
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="列表查询", notes="列表查询")
    @GetMapping(value = "/info", consumes = MediaType.ALL_VALUE)
    public CommonResult<Map<String, Object>> tableInfo(@RequestParam(value = "name") String name) {
        
        SysTableInfo sysTableInfo = sysTableInfoMapper.selectOne(Wrappers.lambdaQuery(SysTableInfo.class)
                .eq(SysTableInfo::getName, name)
                .eq(SysTableInfo::getUseState, UseStateEnum.EFFECTIVE.getCode())
        );
        List<SysTableField> fields = sysTableFieldMapper.selectList(Wrappers.lambdaQuery(SysTableField.class)
                .eq(SysTableField::getTableId, sysTableInfo.getTableId())
                .eq(SysTableField::getUseState, UseStateEnum.EFFECTIVE.getCode())
        );
        Map<String, Object> result = new HashMap(){
            {
                put("table", SysTableInfoVo.buildBy(sysTableInfo, SysTableInfoVo.class));
                put("fields", SysTableFieldVo.buildsBy(fields, SysTableFieldVo.class));
            }
        };
        return CommonResult.success(result);
    }
    /**
    * @Title: tables查询
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="创建", notes="创建")
    @PostMapping(value = "/create")
    public CommonResult<Long> create(@RequestBody SysTableInfoVo vo) {
        SysTableInfo sysTableInfo = SysTableInfo.buildByVo(vo, SysTableInfo.class);
        sysTableInfo.setTableId(SnowflakeIdWorker.getNextId());
        sysTableInfoService.create(sysTableInfo);
        return CommonResult.success(sysTableInfo.getTableId());
    }
    @ApiOperation(value="创建", notes="创建")
    @PostMapping(value = "/createByTable")
    public CommonResult<Object> createByTable(
            @RequestParam("tableId") Long tableId,
            @RequestParam("appId") String appId,
            @RequestBody List<TableColumnVo> vos) {
        List<SysTableField> sysTableFields = vos.stream()
                .map(vo -> TableColumnVo.toSysTableField(vo, tableId, appId)).collect(Collectors.toList());

        sysTableInfoService.createField(sysTableFields);
        removeCache(tableId);
        return CommonResult.success();
    }

    /**
     * @Title: tables查询
     * @Param:
     * @description:
     * @author: wangjie
     * @date: 2020/9/17 14:30
     * @return:
     * @throws:
     */
    @ApiOperation(value="创建", notes="创建")
    @PostMapping(value = "/createField")
    public CommonResult<Object> createField(@RequestBody List<SysTableFieldVo> vos) {
        List<SysTableField> sysTableFields = SysTableField.buildsByVo(vos, SysTableField.class);
        sysTableFields.forEach(sysTableField -> sysTableField.setFieldId(SnowflakeIdWorker.getNextId()));

        sysTableInfoService.createField(sysTableFields);
        removeCache(sysTableFields.get(0).getTableId());
        return CommonResult.success();
    }
    /**
    * @Title: tables查询
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="修改", notes="修改")
    @PostMapping(value = "/updateField")
    public CommonResult<Object> updateField(@RequestBody SysTableFieldVo vo) {
        boolean update = true;
        if(StringUtils.isEmpty(vo.getFieldId())) {
            update = false;
            vo.setFieldId(SnowflakeIdWorker.getNextId());
        }
        SysTableField sysTableField = BaseEntity.buildByVo(vo, SysTableField.class, update);
        if(update) {
            sysTableInfoService.updateField(sysTableField);
        } else {
            sysTableInfoService.createField(Arrays.asList(sysTableField));
        }
        removeCache(sysTableField.getTableId());
        return CommonResult.success();
    }
    
    
    private boolean removeCache(Long tableId) {
        // 清除cache
        SysTableInfo sysTableInfo = sysTableInfoMapper.selectById(tableId);
        Long res = tableHandler.removeByNames(sysTableInfo.getName());
        return res == null ? false : res > 0;
    }
}
