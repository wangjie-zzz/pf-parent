package com.pf.system.model.vo;

import com.pf.bean.SnowflakeIdWorker;
import com.pf.enums.dicts.BoolEnum;
import com.pf.system.constants.enums.FormFieldTypeEnum;
import com.pf.system.model.entity.SysFormField;
import com.pf.util.StringUtils;
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
    
    public static SysFormField toSysFormField(TableColumnVo vo, Long formId, String appId) {
        return SysFormField.builder()
                .appId(appId)
                .formId(formId)
                .fieldId(SnowflakeIdWorker.getNextId())
                .type(FormFieldTypeEnum.INPUT.getCode())
                .value(StringUtils.EMPTY)
                .prop(StringUtils.lineToHump(vo.getColumnName()))
                .label(StringUtils.isEmpty(vo.getColumnComment()) ? StringUtils.lineToHump(vo.getColumnName()) : vo.getColumnComment())
                .labelWidth("80px")
                .placeholder(StringUtils.EMPTY)
                .dict(StringUtils.EMPTY)
                .allowCreate(BoolEnum.FALSE.getCode())
                .clearable(BoolEnum.FALSE.getCode())
                .collapseTags(BoolEnum.FALSE.getCode())
                .disable(BoolEnum.FALSE.getCode())
                .filterable(BoolEnum.FALSE.getCode())
                .hidden(BoolEnum.FALSE.getCode())
                .inlineMessage(BoolEnum.FALSE.getCode())
                .multiple(BoolEnum.FALSE.getCode())
                .remote(BoolEnum.FALSE.getCode())
                .required(BoolEnum.TRUE.getCode())
                .showAllLevels(BoolEnum.FALSE.getCode())
                .showMessage(BoolEnum.TRUE.getCode())
                .showPassword(BoolEnum.FALSE.getCode())
                .spanCol(1)
                .prefix(StringUtils.EMPTY)
                .append(StringUtils.EMPTY)
                .prepend(StringUtils.EMPTY)
                .suffix(StringUtils.EMPTY)
                .build().build();
    }
}
