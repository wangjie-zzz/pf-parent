package com.pf.enums.dicts;


import com.pf.base.BaseErrCode;

/**
 * 登录类型
 */
public enum RoleAuthTypeEnum implements BaseErrCode {
    MENU( 0 ) ,
    INTERFACE( 1) ,
    ;

    private final int code;

    RoleAuthTypeEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
