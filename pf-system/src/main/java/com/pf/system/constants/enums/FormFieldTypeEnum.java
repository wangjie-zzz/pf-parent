package com.pf.system.constants.enums;

/**
 * @ClassName : FormFieldTypeEnum
 * @Description :
 * @Author : wangjie
 * @Date: 2021/10/19-17:38
 */
public enum FormFieldTypeEnum  {
    INPUT( 1 ) ,
    NUMBER( 2 ) ,
    SELECT( 3 ),
    CHECKBOX( 4 ),
    RADIO( 5 ),
    TEXTAREA( 6 ),
    DATE( 7 ),
    TIME( 8 ),
    DATETIME( 9 ),
    CASCADER( 10 ),
    ;

    private final int code;

    FormFieldTypeEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}

