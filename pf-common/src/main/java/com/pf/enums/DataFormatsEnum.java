package com.pf.enums;

import com.pf.base.BaseErrCode;

public enum DataFormatsEnum implements BaseErrCode {
	
	STR( 1, "字符串" ),
	FORM_URLENCODED( 2, "x-www-form-urlencoded(http)"),
	JSON( 3, "json" ),
	BINARY( 4, "二进制" ),
	XML( 5, "xml"),
	FORM_DATA(6, "multipart/form-data(http)" );
	
    private final Integer code;
    private final String info;

    DataFormatsEnum(Integer code, String info) {
        this.code = code;
        this.info = info;
    }

    //根据key获取枚举
    public static DataFormatsEnum getEnumByKey(short code){
        for(DataFormatsEnum temp: DataFormatsEnum.values() ){
            if( temp.getCode() == code ){         	
                return temp;
            }
        }
        return null;
    }
    
    public static String getEnumInfoByKey(Integer code){
        for(DataFormatsEnum temp: DataFormatsEnum.values() ){
            if( temp.getCode() == code ){
                return temp.getMessage();
            }
        }
        return null;
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
