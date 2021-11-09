package com.pf.enums.dicts;


import com.pf.base.BaseErrCode;

/**
 * 使用状态
 */
public enum UseStateEnum implements BaseErrCode {
    /**无效- 逻辑删除**/
    INVALID( 0, "无效" ) ,
    /**有效**/
    EFFECTIVE( 1, "有效" ) ,
    /**冻结-暂时停用，有恢复有效状态的动作**/
    FROZEN( 2, "冻结" ),
    /**废除**/
    ABOLISH(3, "废除");

    private final int code;
    private final String info;

    UseStateEnum(int code, String info) {
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
