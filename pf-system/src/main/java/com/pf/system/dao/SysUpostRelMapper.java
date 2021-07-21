package com.pf.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pf.system.model.entity.SysUpostRel;
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
public interface SysUpostRelMapper extends BaseMapper<SysUpostRel> {

    List<SysUpostRel> selectListAndUserName(@Param("postId") String postId);
}
