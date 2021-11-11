package com.pf.system.model.vo;

import com.pf.base.BaseVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="SysFormInfo对象", description="")
public class SysTableInfoVo extends BaseVo<SysTableInfoVo> {
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
}
