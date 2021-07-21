package com.pf.constant;

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

    class CACHE_KEY {

        public static  final String SYS_USER_INFO_KEY_PREFIX = "sysUserInfo-";
        public static  final Integer EXPIRATION_TIME = 2 * 60 * 60;
    }
}
