package com.pf.system.constants.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName : DictFieldEnum
 * @Description :
 * @Author : wangjie
 * @Date: 2021/11/9-9:54
 */
public enum DictFieldEnum {
    BOOLEAN("BOOLEAN"),
    USE_STATE("USE_STATE"),
    ;
    String field;
    DictFieldEnum(String field) {
        this.field = field;
    }
    public String getField() {
        return field;
    }
    public static DictFieldEnum getBy( String name ) {
        return Arrays.asList(DictFieldEnum.values()).stream().filter(fieldEnum -> {
            return fieldEnum.getField().equals(name);
        }).findFirst().get();
    }
    public static List<DictFieldEnum> getsBy(List<String> names ) {
        return Arrays.asList(DictFieldEnum.values()).stream().filter(f -> {
            return names.contains(f.getField());
        }).collect(Collectors.toList());
    }
}
