package com.pf.enums.dicts;


import com.pf.base.BaseErrCode;

/**
 * 使用状态
 */
public enum UseStateEnum {
    /**无效- 逻辑删除**/
    INVALID( 0 ) ,
    /**有效**/
    EFFECTIVE( 1 ) ,
    /**冻结-暂时停用，有恢复有效状态的动作**/
    FROZEN( 2 ),
    /**废除**/
    ABOLISH(3);

    private final int code;

    UseStateEnum(int code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
    
    public String getCodeToStr() {
        return String.valueOf(code);
    }
}
