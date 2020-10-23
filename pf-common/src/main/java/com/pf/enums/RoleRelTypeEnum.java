package com.pf.enums;


import com.pf.base.BaseErrCode;

/**
 * 登录类型
 */
public enum RoleRelTypeEnum implements BaseErrCode {
    USER( 0, "用户角色" ) ,
    POST( 1, "岗位角色" ) ,
    ;

    private final int code;
    private final String info;

    RoleRelTypeEnum(int code, String info) {
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
