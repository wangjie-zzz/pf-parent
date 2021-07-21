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

    String AUTH_SERVER_URI = "http://pf-auth/pf-auth/oauth/token";

    String CLIENT_CREDENTIALS = "pf-web:123456";
    String DEFAULT_PWD = new BCryptPasswordEncoder().encode("123456");
}
