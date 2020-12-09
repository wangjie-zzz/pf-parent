package com.pf.constant;

/**
 * @ClassName : SysConstants
 * @Description : 系统常量
 */
public interface SysGeneralConsts {

    /** 统一公开端点 **/
    String PERMIT_ENDPOINT = "/permitAll";

    class REDIS_KEY_PREFIX {
        public static final String SYS_CHECK_RESUBMIT = "redis-lock-";
    };
}
