package com.pf.enums.dicts;


import com.pf.base.BaseErrCode;

/**
 * 登录类型
 */
public enum UserDataSourceEnum {
    WEB_REGISTER( 0) ,
    OUT_IMPORT( 1 ) ,
    ADMIN_CREATE( 2 ) ,
    ;

    private final int code;

    UserDataSourceEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
