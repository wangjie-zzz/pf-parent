package com.pf.system.model.dto;

import com.pf.system.model.entity.SysTableField;
import com.pf.util.JacksonsUtils;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author pf
 * @since 2021-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysTableField对象", description="")
public class TableFieldDto implements Serializable {

    private static final long serialVersionUID=1L;


    private Long fieldId;

    private String appId;
    private Long tableId;
    private String prop;

    private String label;

    private String dict;

    private Integer type;

    private Integer reserveSelection;

    private String width;

    private String minWidth;

    private Integer fixed;

    private String columnKey;

    private Integer sortable;

    private String sortBy;

    private String sortOrders;

    private Integer resizable;

    private Integer showOverflowTooltip;

    private Integer align;

    private Integer headerAlign;

    public static TableFieldDto buildBy(SysTableField sysTableField) {
        return JacksonsUtils.copy(sysTableField, TableFieldDto.class);
    }
}
