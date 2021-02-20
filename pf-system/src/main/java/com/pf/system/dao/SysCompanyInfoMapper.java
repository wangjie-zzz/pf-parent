package com.pf.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pf.system.model.entity.SysCompanyInfo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2020-09-15
 */
public interface SysCompanyInfoMapper extends BaseMapper<SysCompanyInfo> {

    /*组织架构树查询*/
    List<SysCompanyInfo> selectListWithDept();
}
