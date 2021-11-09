//package com.pf.system.constants.enums;
//
//import com.pf.base.BaseErrCode;
//
///**
// * @ClassName : LabelPositionEnum
// * @Description :
// * @Author : wangjie
// * @Date: 2021/10/19-17:38
// */
//public enum LabelPositionEnum  implements BaseErrCode {
//    /**无效- 逻辑删除**/
//    RIGHT( 0, "right" ) ,
//    /**有效**/
//    LEFT( 1, "left" ) ,
//    /**冻结-暂时停用，有恢复有效状态的动作**/
//    TOP( 2, "top" ),
//    ;
//
//    private final int code;
//    private final String info;
//
//    LabelPositionEnum(int code, String info) {
//        this.code = code;
//        this.info = info;
//    }
//
//    public Integer getCode() {
//        return code;
//    }
//
//    @Override
//    public String getMessage() {
//        return info;
//    }
//
//    @Override
//    public String getCodeToStr() {
//        return String.valueOf(code);
//    }
//}
//
