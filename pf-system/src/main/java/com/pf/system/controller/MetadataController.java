package com.pf.system.controller;

import com.pf.base.CommonResult;
import com.pf.system.constants.SystemConstants;
import com.pf.system.dao.MetadataMapper;
import com.pf.system.model.entity.SysAppInfo;
import com.pf.system.model.vo.TableColumnVo;
import com.pf.system.service.ISysAppInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName : MetadataController
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-11:16
 */
@Api(value = "元数据controller", tags = {"元数据接口"})
@RestController
@RequestMapping(value = SystemConstants.MS_API_PREFIX + MetadataController.API_PREFIX, produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
public class MetadataController {

    public final static String API_PREFIX = "/metadata";

    @Autowired
    private MetadataMapper metadataMapper;

    /**
    * @Title: dbs查询
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="db查询", notes="db查询")
    @GetMapping(value = "/dbNames", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<String>> getDbNames() {
        return CommonResult.success(metadataMapper.getDbNames());
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
    @ApiOperation(value="tables查询", notes="tables查询")
    @GetMapping(value = "/getTableNamesByDb", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<String>> getTableNamesByDbName(@RequestParam(value = "dbName") String dbName) {
        return CommonResult.success(metadataMapper.getTableNamesByDbName(dbName));
    }
    /**
    * @Title: 列数据查询
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 14:30
    * @return:
    * @throws:
    */
    @ApiOperation(value="列数据查询", notes="列数据查询")
    @GetMapping(value = "/getTableNamesByDbAndTb", consumes = MediaType.ALL_VALUE)
    public CommonResult<List<TableColumnVo>> getColumnsByTableAndDb(@RequestParam(value = "dbName") String dbName, @RequestParam(value = "tbName") String tbName) {
        return CommonResult.success(metadataMapper.getColumnsByTableAndDb(dbName, tbName));
    }
}
