package com.pf.system.model.dto;

import com.pf.system.model.entity.SysTableInfo;
import com.pf.util.JacksonsUtils;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
public class TableInfoDto implements Serializable {

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
    private List<TableFieldDto> fieldDtos;

    public static TableInfoDto buildBy(SysTableInfo table) {
        return JacksonsUtils.copy(table, TableInfoDto.class);
    }
}
