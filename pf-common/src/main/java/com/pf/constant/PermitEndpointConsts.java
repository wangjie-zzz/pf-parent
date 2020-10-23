package com.pf.constant;

/**
 * @ClassName : PermitEndpointConsts
 * @Description : PermitEndpointConsts
 */
public interface PermitEndpointConsts {

    String[] COMMON = {
        /*内置端点*/
        "/favicon.ico",
        "/webjars/**",
        "/actuator/**",
        "/oauth/**",
        /*自定义开放端口*/
        "/**" + SysGeneralConsts.PERMIT_ENDPOINT + "/**",
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
    String[] AUTH = {
        /*内置端点*/
        "/rsa/publicKey",
        "/rsa/test1",
        "/rsa/test2",
    };
    String[] PROTAL = {
        /*内置端点*/
    };

}
