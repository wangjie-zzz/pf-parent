package com.pf.enums;


import com.pf.base.BaseErrCode;

public enum QuarterNumEnum implements BaseErrCode {
    
    QUARTER_FIRST(1,"第一季度"),
    QUARTER_SECOND(2,"第二季度"),
    QUARTER_THIRD(3,"第三季度"),
    QUARTER_FOURTH(4,"第四季度");
    
    private final int code;
    private final String info;
    
    QuarterNumEnum(int code, String info) {
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
        return String.valueOf(info);
    }
}
