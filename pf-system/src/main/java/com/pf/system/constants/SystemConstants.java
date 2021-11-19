package com.pf.system.constants;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName : MsGeneralConsts
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-11:20
 */
public interface SystemConstants {

    /** API统一版本版本 **/
    String MS_API_PREFIX = "/api/v1";

    String DEFAULT_PWD = new BCryptPasswordEncoder().encode("123456");
    
    interface REDIS_KEY {
        String dict = "BASE:DICT";
        String form = "BASE:FORM";
        String table = "BASE:TABLE";
    }
}
