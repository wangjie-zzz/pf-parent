package com.pf.enums.dicts;


import com.pf.base.BaseErrCode;

/**
 * 登录类型
 */
public enum UserDataSourceEnum implements BaseErrCode {
    WEB_REGISTER( 0, "网络注册" ) ,
    OUT_IMPORT( 1, "外部导入" ) ,
    ADMIN_CREATE( 2, "后台插入" ) ,
    ;

    private final int code;
    private final String info;

    UserDataSourceEnum(int code, String info) {
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
