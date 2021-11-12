package com.pf.enums.dicts;

/**
 * 登录类型
 */
public enum RoleAuthTypeEnum {
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
