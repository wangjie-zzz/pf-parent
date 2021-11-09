package com.pf.system.dao;

import com.pf.system.model.vo.TableColumnVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName : MetadataMapper
 * @Description :
 * @Author : wangjie
 * @Date: 2021/10/14-15:11
 */
public interface MetadataMapper {
    List<String> getDbNames();
    List<String> getTableNamesByDbName(@Param("dbName") String dbName);
    List<TableColumnVo> getColumnsByTableAndDb(@Param("dbName")String dbName, @Param("tbName") String tbName);
}
