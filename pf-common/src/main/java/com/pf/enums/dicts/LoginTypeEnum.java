package com.pf.enums.dicts;


import com.pf.base.BaseErrCode;

/**
 * 登录类型
 */
public enum LoginTypeEnum implements BaseErrCode {
    PHONE( 0, "手机号" ) ,
    USER_CODE( 1, "用户账号" ) ,
    ;

    private final int code;
    private final String info;

    LoginTypeEnum(int code, String info) {
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
