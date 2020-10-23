package com.pf.constant;

import io.swagger.models.auth.In;

/**
 * @ClassName : CacheConstants
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/9/17-17:15
 */
public interface CacheConstants {

    public final String SYS_USER_INFO_KEY_PREFIX = "sysUserInfo-";
    public final Integer EXPIRATION_TIME = 2 * 60 * 60;
}
