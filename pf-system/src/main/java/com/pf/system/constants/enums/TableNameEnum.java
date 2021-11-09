package com.pf.system.constants.enums;

/**
 * @ClassName : DictFieldEnum
 * @Description :
 * @Author : wangjie
 * @Date: 2021/11/9-9:54
 */
public enum TableNameEnum {
    BOOL("BOOL"),
    USE_STATE("USE_STATE"),
    ;
    String name;
    TableNameEnum(String field) {
        this.name = field;
    }
    public String getName() {
        return name;
    }
}
