package com.pf.test.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pf.test.model.entity.TestA;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pf
 * @since 2020-11-06
 */
public interface TestAMapper extends BaseMapper<TestA> {

    public TestA fuSelectById(@Param("test1") String test1);
}
