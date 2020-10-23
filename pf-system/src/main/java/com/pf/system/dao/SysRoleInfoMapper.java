package com.pf.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pf.system.model.entity.SysRoleInfo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author
 * @since 2020-09-15
 */
public interface SysRoleInfoMapper extends BaseMapper<SysRoleInfo> {


    /**
    * @Title: 查询角色（用户Id+岗位Id）
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 10:56
    * @return:
    * @throws:
    */
    public List<SysRoleInfo> selectRoleByUserAndPost(List<String> list);
}
