package com.pf.system.constants.enums;

import com.pf.base.BaseErrCode;

/**
 * @ClassName : LabelPositionEnum
 * @Description :
 * @Author : wangjie
 * @Date: 2021/10/19-17:38
 */
public enum FormFieldTypeEnum implements BaseErrCode {
    INPUT( 1, "输入框" ) ,
//    NUMBER( 2, "数字框" ) ,
//    SELECT( 3, "下拉选择框" ),
//    CHECKBOX( 4, "多选框" ),
//    RADIO( 5, "单选框" ),
//    TEXTAREA( 6, "文本框" ),
//    DATE( 7, "日期框" ),
//    TIME( 8, "时间框" ),
//    DATETIME( 9, "日期时间框" ),
//    CASCADER( 10, "级联选择框" ),
    ;

    private final int code;
    private final String info;

    FormFieldTypeEnum(int code, String info) {
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

