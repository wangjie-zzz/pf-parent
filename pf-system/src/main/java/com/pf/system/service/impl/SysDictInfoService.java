package com.pf.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pf.base.CommonResult;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.enums.SysStatusCode;
import com.pf.system.dao.SysDictInfoMapper;
import com.pf.system.model.entity.SysDictInfo;
import com.pf.system.model.entity.SysUserInfo;
import com.pf.system.service.ISysDictInfoService;
import com.pf.util.Asserts;
import com.pf.util.CacheDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName : SysUsreInfoService
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-10:19
 */
@Service
public class SysDictInfoService implements ISysDictInfoService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SysDictInfoMapper sysDictInfoMapper;

    @Override
    public CommonResult<List<SysDictInfo>> list() {
        return CommonResult.success(sysDictInfoMapper.selectList(Wrappers.emptyWrapper()));
    }

    @Override
    public CommonResult<Object> update(SysDictInfo sysDictInfo) {
        SysUserInfo sysUserInfo = CacheDataUtil.getUserCacheBean(redisTemplate);
        if(sysUserInfo == null) {
            Asserts.fail(SysStatusCode.UNAUTHORIZED);
            return null;
        }
        sysDictInfo.setDictUpdDate(LocalDateTime.now());
        sysDictInfo.setDictUpdUser(sysUserInfo.getUserId());
        if(StringUtils.isEmpty(sysDictInfo.getDictId())) {
            sysDictInfo.setDictId(SnowflakeIdWorker.getNextId());
            sysDictInfo.setDictIntDate(LocalDateTime.now());
            sysDictInfo.setDictIntUser(sysUserInfo.getUserId());
            sysDictInfoMapper.insert(sysDictInfo);
        }else {
            sysDictInfoMapper.updateById(sysDictInfo);
        }
        return CommonResult.success();
    }

    @Override
    public CommonResult<Object> delete(List<String> ids) {
        sysDictInfoMapper.deleteBatchIds(ids);
        return CommonResult.success();
    }
}
