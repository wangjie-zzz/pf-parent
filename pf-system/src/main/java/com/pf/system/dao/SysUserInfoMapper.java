package com.pf.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pf.system.model.entity.SysUserInfo;
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
public interface SysUserInfoMapper extends BaseMapper<SysUserInfo> {

    /**
    * @Title: 查询用户+岗位信息
    * @Param:
    * @description:
    * @author: wangjie
    * @date: 2020/9/17 11:03
    * @return:
    * @throws:
    */
    SysUserInfo selectUserAndPostInfo (@Param("type") Integer type,@Param("code") Long code);

    List<SysUserInfo> selectByDeptId(@Param("deptId") Long deptId);

    SysUserInfo selectByUserCode(@Param("userCode") Long userCode);
}
