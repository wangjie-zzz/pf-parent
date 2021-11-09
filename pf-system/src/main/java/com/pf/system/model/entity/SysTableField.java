package com.pf.system.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class SysTableField implements Serializable {

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

    private Integer useState;

    private Long updateUser;

    private LocalDateTime updateTime;

    private Long createUser;

    private LocalDateTime createTime;


}
