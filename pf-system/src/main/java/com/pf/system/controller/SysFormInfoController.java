package com.pf.system.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.aop.context.UserContext;
import com.pf.base.BaseEntity;
import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.enums.dicts.UseStateEnum;
import com.pf.model.UserDto;
import com.pf.system.component.FormHandler;
import com.pf.system.constants.SystemConstants;
import com.pf.system.dao.SysFormFieldMapper;
import com.pf.system.dao.SysFormInfoMapper;
import com.pf.system.model.dto.FormInfoDto;
import com.pf.system.model.entity.SysFormField;
import com.pf.system.model.entity.SysFormInfo;
import com.pf.system.model.vo.SysFormFieldVo;
import com.pf.system.model.vo.SysFormInfoVo;
import com.pf.system.model.vo.TableColumnVo;
import com.pf.system.service.impl.SysFormInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
@Api(value = "表单配置controller", tags = {"表单配置接口"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysFormInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysFormInfoController {

    public final static String API_PREFIX = "/sysFormInfo";

    @Autowired
    private SysFormInfoMapper sysFormInfoMapper;
    @Autowired
    private SysFormFieldMapper sysFormFieldMapper;
    @Autowired
    private SysFormInfoService sysFormInfoService;
    @Autowired
    private FormHandler formHandler;

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
    public CommonResult<List<FormInfoDto>> getCacheList(@RequestBody List<String > formNames) {
        List<FormInfoDto> formInfoDtos = formHandler.getByNames(formNames);
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
    public CommonResult<List<SysFormInfoVo>> getFormList() {
        return CommonResult.success(SysFormInfoVo.buildsBy(
                sysFormInfoMapper.selectList(Wrappers.lambdaQuery(SysFormInfo.class)
                        .eq(SysFormInfo::getUseState, UseStateEnum.EFFECTIVE.getCode())),
                SysFormInfoVo.class)
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
    public CommonResult<Map<String, Object>> formInfo(@RequestParam(value = "name") String name) {
        
        SysFormInfo sysFormInfo = sysFormInfoMapper.selectOne(Wrappers.lambdaQuery(SysFormInfo.class)
                .eq(SysFormInfo::getName, name)
                .eq(SysFormInfo::getUseState, UseStateEnum.EFFECTIVE.getCode())
        );
        List<SysFormField> formFields = sysFormFieldMapper.selectList(Wrappers.lambdaQuery(SysFormField.class)
                .eq(SysFormField::getFormId, sysFormInfo.getFormId())
                .eq(SysFormField::getUseState, UseStateEnum.EFFECTIVE.getCode())
        );
        Map<String, Object> result = new HashMap(){
            {
                put("form", SysFormInfoVo.buildBy(sysFormInfo, SysFormInfoVo.class));
                put("fields", SysFormFieldVo.buildsBy(formFields, SysFormFieldVo.class));
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
    @PostMapping(value = "/createForm")
    public CommonResult<Long> createForm(@RequestBody SysFormInfoVo vo) {
        SysFormInfo sysFormInfo = SysFormInfo.buildByVo(vo, SysFormInfo.class);
        sysFormInfo.setFormId(SnowflakeIdWorker.getNextId());
        sysFormInfoService.createForm(sysFormInfo);
        return CommonResult.success(sysFormInfo.getFormId());
    }
    @ApiOperation(value="创建", notes="创建")
    @PostMapping(value = "/createByTable")
    public CommonResult<Object> createByTable(
            @RequestParam("formId") Long formId,
            @RequestParam("appId") String appId,
            @RequestBody List<TableColumnVo> vos) {
        List<SysFormField> sysFormFields = vos.stream()
                .map(vo -> TableColumnVo.toSysFormField(vo, formId, appId)).collect(Collectors.toList());

        sysFormInfoService.createFormField(sysFormFields);
        removeCache(formId);
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
    /*@ApiOperation(value="创建", notes="创建")
    @PostMapping(value = "/createFormField")
    public CommonResult<Object> createFormField(@RequestBody List<SysFormFieldVo> vos) {
        List<SysFormField> sysFormFields = SysFormField.buildsByVo(vos, SysFormField.class);
        sysFormFields.forEach(sysFormField -> sysFormField.setFieldId(SnowflakeIdWorker.getNextId()));
        
        sysFormInfoService.createFormField(sysFormFields);
        removeCache(sysFormFields.get(0).getFormId());
        return CommonResult.success();
    }*/
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
    @PostMapping(value = "/updateFormField")
    public CommonResult<Object> updateFormField(@RequestBody SysFormFieldVo vo) {
        boolean update = true;
        if(StringUtils.isEmpty(vo.getFieldId())) {
            update = false;
            vo.setFieldId(SnowflakeIdWorker.getNextId());
        }
        SysFormField sysFormField = BaseEntity.buildByVo(vo, SysFormField.class, update);
        if(update) {
            sysFormInfoService.updateFormField(sysFormField);
        } else {
            sysFormInfoService.createFormField(Arrays.asList(sysFormField));
        }
        removeCache(sysFormField.getFormId());
        return CommonResult.success();
    }
    
    
    private boolean removeCache(Long formId) {
        // 清除cache
        SysFormInfo sysFormInfo = sysFormInfoMapper.selectById(formId);
        Long res = formHandler.removeByNames(sysFormInfo.getName());
        return res == null ? false : res > 0;
    }
}
