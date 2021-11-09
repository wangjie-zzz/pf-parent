package com.pf.system.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName : TableColumnVo
 * @Description :
 * @Author : wangjie
 * @Date: 2021/10/14-15:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableColumnVo {
    private String tableSchema;
    private String tableName;
    private String isNullable;
    private String dataType;
    private String columnName;
    private String columnKey;
    private String columnDefault;
    private String columnType;
    private String columnComment;
}
