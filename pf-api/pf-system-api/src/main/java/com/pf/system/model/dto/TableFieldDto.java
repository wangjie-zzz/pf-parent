package com.pf.system.model.dto;

import com.pf.base.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class TableFieldDto extends BaseDto<TableFieldDto> {

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

}
