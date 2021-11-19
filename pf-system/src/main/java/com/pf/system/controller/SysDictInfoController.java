package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.system.component.DictHandler;
import com.pf.system.constants.SystemConstants;
import com.pf.system.constants.enums.DictFieldEnum;
import com.pf.system.model.dto.DictInfoDto;
import com.pf.system.model.entity.SysDictInfo;
import com.pf.system.model.vo.SysDictInfoVo;
import com.pf.system.service.impl.SysDictInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName : SysCompanyInfoController
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-11:16
 */
@Api(value = "系统字典controller", tags = {"系统字典操作接口"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + SysDictInfoController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class SysDictInfoController {

    public final static String API_PREFIX = "/sysDictInfo";

    @Autowired
    private SysDictInfoService sysDictInfoService;

    @Autowired
    private DictHandler dictHandler;

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
    public CommonResult<Map<String, List<DictInfoDto>>> getCacheList(@RequestBody List<String > fields) {
        Map<String, List<DictInfoDto>> fieldMap = new HashMap<>();
        if(!CollectionUtils.isEmpty(fields)) {
            fields.forEach(field -> {
                List<DictInfoDto> dictInfoDtos = dictHandler.getByField(DictFieldEnum.getBy(field));
                fieldMap.put(field, dictInfoDtos);
            });
        }
        return CommonResult.success(fieldMap);
    }
    @ApiOperation(value="查询所有", notes="查询所有")
    @GetMapping(value = "/list", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<SysDictInfoVo>> list() {
        List<SysDictInfo> dictInfos = sysDictInfoService.list();
        return CommonResult.success(SysDictInfoVo.buildsBy(dictInfos, SysDictInfoVo.class));
    }
    @ApiOperation(value="删除", notes="删除")
    @PostMapping(value = "/delete")
    public CommonResult<Integer> delete(@RequestBody List<String> ids) {
        return CommonResult.success(sysDictInfoService.delete(ids));
    }
    @ApiOperation(value="新增/删除", notes="新增/删除")
    @PostMapping(value = "/update")
    public CommonResult<Integer> update(@RequestBody SysDictInfoVo vo) {
        if(StringUtils.isEmpty(vo.getDictId())) {
            vo.setDictId(SnowflakeIdWorker.getNextId());
            return CommonResult.success(sysDictInfoService.insert(SysDictInfo.buildByVo(vo,SysDictInfo.class)));
        }
        return CommonResult.success(sysDictInfoService.update(SysDictInfo.buildByVo(vo,SysDictInfo.class, true)));
    }
}
