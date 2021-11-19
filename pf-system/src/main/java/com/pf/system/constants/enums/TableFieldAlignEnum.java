package com.pf.system.constants.enums;

/**
 * @ClassName : TableFieldTypeEnum
 * @Description :
 * @Author : wangjie
 * @Date: 2021/10/19-17:38
 */
public enum TableFieldAlignEnum {
    LEFT( 0 ),
    RIGHT( 1 ) ,
    CENTER( 2) ,
    ;

    private final int code;

    TableFieldAlignEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}

