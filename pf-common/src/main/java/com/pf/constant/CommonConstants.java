package com.pf.constant;

import com.pf.util.HttpHeaderUtil;

/**
 * @ClassName : CommonConstants
 * @Description : CommonConstants
 */
public interface CommonConstants {
    /** 统一公开端点 **/
    String PERMIT_ENDPOINT = "/permitAll";

    String[] COMMON_PERMIT_ENDPOINT = {
        /*内置端点*/
        "/favicon.ico",
        "/webjars/**",
        "/actuator/**",
        /*自定义开放端口*/
        "/**" + PERMIT_ENDPOINT + "/**",
        /*swagger页面及资源文件*/
        "/swagger-ui.html",
        "/doc.html",
        "/swagger-resources/**",
        "/v2/api-docs/**",
        /*静态资源文件*/
        "/**/css/**",
        "/**/js/**",
        "/**/plugin/**",
        "/**/templates/**",
        "/**/img/**",
        "/**/fonts/**"
    };
    interface CACHE_KEY {
        String USER = "pf:user";
        String SESSION = "pf:session";
    }
}
