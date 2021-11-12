package com.pf.enums.dicts;

/**
 * 登录类型
 */
public enum RoleRelTypeEnum {
    USER( 0) ,
    POST( 1) ,
    ;

    private final int code;

    RoleRelTypeEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
