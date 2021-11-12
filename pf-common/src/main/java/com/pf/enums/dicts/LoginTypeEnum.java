package com.pf.enums.dicts;

/**
 * 登录类型
 */
public enum LoginTypeEnum {
    PHONE( 0 ) ,
    USER_CODE( 1) ,
    ;

    private final int code;

    LoginTypeEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
