package com.pf.auth.constant;

import com.pf.util.HttpHeaderUtil;

/**
 * 消息常量
 * Created by  on 2020/6/19.
 */
public interface AuthConstants {

    String USERNAME_PASSWORD_ERROR = "用户名或密码错误!";

    String CREDENTIALS_EXPIRED = "该账户的登录凭证已过期，请重新登录!";

    String ACCOUNT_DISABLED = "该账户已被禁用，请联系管理员!";

    String ACCOUNT_LOCKED = "该账号已被锁定，请联系管理员!";

    String ACCOUNT_EXPIRED = "该账号已过期，请联系管理员!";
    
    String LOGIN_PAGE = "/sso/login";

    String[] PERMIT_ENDPOINTS = {
            /*内置端点*/
            /*SSO模式下，oauth的端点需要进行登录校验*/
            /*"/oauth/**",*/
            "/rsa/publicKey",
    };
    String TOKEN_URL = "http://localhost:8401/oauth/token";

    class Jwt {

        /** 密钥库的路径及名称 **/
        public final static String KEYSTORE = "jwt.jks";

        /** 别名 **/
        public final static String ALIAS = "jwt";

        /** 别名条目的密码(私钥的密码) **/
        public final static String KEYPASS = "123456";

    }
}
