package com.pf.system.model.vo;

import com.pf.bean.SnowflakeIdWorker;
import com.pf.enums.dicts.BoolEnum;
import com.pf.system.constants.enums.DictFieldEnum;
import com.pf.system.constants.enums.FormFieldTypeEnum;
import com.pf.system.constants.enums.TableFieldAlignEnum;
import com.pf.system.constants.enums.TableFieldTypeEnum;
import com.pf.system.model.entity.SysFormField;
import com.pf.system.model.entity.SysTableField;
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
        FormFieldTypeEnum formFieldTypeEnum = vo.getType();
        return SysFormField.builder()
                .appId(appId)
                .formId(formId)
                .fieldId(SnowflakeIdWorker.getNextId())
                .type(formFieldTypeEnum.getCode())
                .value(StringUtils.EMPTY)
                .prop(StringUtils.lineToHump(vo.getColumnName()))
                .label(StringUtils.isEmpty(vo.getColumnComment()) ? StringUtils.lineToHump(vo.getColumnName()) : vo.getColumnComment())
                .labelWidth("80px")
                .placeholder(StringUtils.EMPTY)
                .dict(
                        formFieldTypeEnum == FormFieldTypeEnum.SELECT ?
                                DictFieldEnum.BOOLEAN.getField() : StringUtils.EMPTY)
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
    /*
    * TODO 完善数据类型对应的表单项类型
    * */
    private FormFieldTypeEnum getType() {
        FormFieldTypeEnum formFieldTypeEnum;
        switch (dataType) {
            case "datetime":
                formFieldTypeEnum = FormFieldTypeEnum.DATETIME;
                break;
            case "date":
                formFieldTypeEnum = FormFieldTypeEnum.DATE;
                break;
            case "time":
                formFieldTypeEnum = FormFieldTypeEnum.TIME;
                break;
            case "int":
            case "bigint":
                formFieldTypeEnum = FormFieldTypeEnum.NUMBER;
                break;
            case "tinyint":
                formFieldTypeEnum = FormFieldTypeEnum.SELECT;
                break;
            case "text":
                formFieldTypeEnum = FormFieldTypeEnum.TEXTAREA;
                break;
            case "":
                formFieldTypeEnum = FormFieldTypeEnum.CASCADER;
                formFieldTypeEnum = FormFieldTypeEnum.RADIO;
                formFieldTypeEnum = FormFieldTypeEnum.CHECKBOX;
                break;
            default:
                formFieldTypeEnum = FormFieldTypeEnum.INPUT;
        }
        return formFieldTypeEnum;
    }

    public static SysTableField toSysTableField(TableColumnVo vo, Long tableId, String appId) {
        return SysTableField.builder()
                .appId(appId)
                .tableId(tableId)
                .fieldId(SnowflakeIdWorker.getNextId())
                .prop(StringUtils.lineToHump(vo.getColumnName()))
                .label(StringUtils.isEmpty(vo.getColumnComment()) ? StringUtils.lineToHump(vo.getColumnName()) : vo.getColumnComment())
                .type(TableFieldTypeEnum.NORMAL.getCode())
                .dict(StringUtils.EMPTY)
                .reserveSelection(BoolEnum.TRUE.getCode())
                .width(StringUtils.EMPTY)
                .minWidth(StringUtils.EMPTY)
                .fixed(BoolEnum.FALSE.getCode())
                .columnKey(StringUtils.EMPTY)
                .sortable(BoolEnum.TRUE.getCode())
                .sortBy(StringUtils.EMPTY)
                .sortOrders(StringUtils.EMPTY)
                .resizable(BoolEnum.TRUE.getCode())
                .showOverflowTooltip(BoolEnum.TRUE.getCode())
                .align(TableFieldAlignEnum.CENTER.getCode())
                .headerAlign(TableFieldAlignEnum.CENTER.getCode())
                .build().build();
    }
}
