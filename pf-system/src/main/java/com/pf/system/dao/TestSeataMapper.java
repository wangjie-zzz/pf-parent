package com.pf.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pf.system.model.entity.SysAppInfo;
import com.pf.test.model.TestADto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2020-09-15
 */
public interface TestSeataMapper extends BaseMapper<TestADto> {

    int updateTestA(@Param("obj") TestADto testADto);
}
