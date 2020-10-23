package com.pf.enums;


import com.pf.base.BaseErrCode;

/**
 * 登录类型
 */
public enum RoleTypeEnum implements BaseErrCode {
    GUEST( 0, "游客" ) ,
    ADMIN( 1, "管理员" ) ,
    SADMIN( 2, "超级管理员" ) ,
    ;

    private final int code;
    private final String info;

    RoleTypeEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return info;
    }

    @Override
    public String getCodeToStr() {
        return String.valueOf(code);
    }
}
