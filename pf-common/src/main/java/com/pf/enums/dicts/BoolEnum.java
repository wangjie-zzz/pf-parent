package com.pf.enums.dicts;


import com.pf.base.BaseErrCode;

/**
 * 使用状态
 */
public enum BoolEnum {
    TRUE( 1) ,
    FALSE( 0 ) ,
;
    private final int code;

    BoolEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
