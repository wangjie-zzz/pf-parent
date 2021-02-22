package com.pf.plat.constants;

/**
 * @ClassName : MsGeneralConsts
 * @Description :
 * @Author : wangjie
 * @Date: 2020/9/17-11:20
 */
public class PlatGeneralConsts {

    /** API统一版本版本 **/
    public final static String MS_API_PREFIX = "/api/v1";

    /*用户初始密码明文*/
    public final static String INITIAL_PASSWORD = "123456";

    public final class Signature {
        public static final String APP_ID_KEY = "appId";
        public static final String TIMESTAMP_QUERY = "timestamp";
        public static final String NONCE_QUERY = "nonce";
        public static final String SIGN_HEADER = "X-Sign";
        public static final String DEFAULT_APP_SECRET = "appSecret";
    }
}
