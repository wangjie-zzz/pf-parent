package com.pf.system.constants.enums;

import com.pf.base.BaseErrCode;

/**
 * @ClassName : TableFieldTypeEnum
 * @Description :
 * @Author : wangjie
 * @Date: 2021/10/19-17:38
 */
public enum TableFieldTypeEnum {
    SELECTION( 0 ),
    EXPAND( 1 ) ,
    INDEX( 2 ) ,
    NORMAL( 3 ),
    ;

    private final int code;

    TableFieldTypeEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}

