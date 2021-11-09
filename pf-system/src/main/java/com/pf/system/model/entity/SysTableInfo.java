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
@ApiModel(value="SysTableInfo对象", description="")
public class SysTableInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long tableId;
    private String appId;

    private String name;

    private Integer showPage;

    private String height;

    private String maxHeight;

    private Integer stripe;

    private Integer border;

    private Integer size;

    private Integer fit;

    private Integer showHeader;

    private Integer highlightCurrentRow;

    private String rowKey;

    private String emptyText;

    private Integer showSummary;

    private String sumText;

    private Integer selectOnIndeterminate;

    private Integer useState;

    private Long updateUser;

    private LocalDateTime updateTime;

    private Long createUser;

    private LocalDateTime createTime;


}
