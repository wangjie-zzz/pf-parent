package com.pf.enums;

import com.pf.base.BaseErrCode;

public enum DateFormatTypeEnum implements BaseErrCode {
    
	/** 短日期格式 */
    SHORT_DATE(0,"yyyyMMdd"),
    /** 长日期格式 */
    LONG_DATE(1,"yyyy-MM-dd"),
    /** 简单时间格式 */
    SIMPLE_DATETIME(2,"yyyyMMdd HHmmss"),
    /** 短时间格式 */
    SHORT_DATETIME(3,"yyyy-MM-dd HH:mm"),
    /** 长时间格式 */
    LONG_DATETIME(4,"yyyy-MM-dd HH:mm:ss"),
    /** 导入数据日期格式 */
    IMPORT_DATE(5,"yyyy/MM/dd");
    
    private final Integer code;
    private final String info;
    
    DateFormatTypeEnum(Integer code, String info) {
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
