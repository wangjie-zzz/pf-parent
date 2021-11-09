package com.pf.enums.dicts;


import com.pf.base.BaseErrCode;

/**
 * 使用状态
 */
public enum BoolEnum implements BaseErrCode {
    TRUE( 1, "是" ) ,
    FALSE( 0, "否" ) ,
;
    private final int code;
    private final String info;

    BoolEnum(int code, String info) {
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
