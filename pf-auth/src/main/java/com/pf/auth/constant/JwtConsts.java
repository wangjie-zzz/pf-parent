package com.pf.auth.constant;

/**
 * @ClassName : JwtConsts
 * @Description : Jwt
 */
public interface JwtConsts {

    /** 密钥库的路径及名称 **/
    String KEYSTORE = "jwt.jks";

    /** 别名 **/
    String ALIAS = "jwt";

    /** 别名条目的密码(私钥的密码) **/
    String KEYPASS = "123456";

}
