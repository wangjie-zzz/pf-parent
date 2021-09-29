package com.pf.enums;


import com.pf.base.BaseErrCode;

/**
 * 登录类型
 */
public enum RoleAuthTypeEnum implements BaseErrCode {
    MENU( 0, "菜单权限" ) ,
    INTERFACE( 1, "接口权限" ) ,
    ;

    private final int code;
    private final String info;

    RoleAuthTypeEnum(int code, String info) {
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
