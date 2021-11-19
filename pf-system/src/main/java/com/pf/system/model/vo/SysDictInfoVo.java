package com.pf.system.model.vo;

import com.pf.base.BaseEntity;
import com.pf.base.BaseVo;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author pf
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysDictInfo对象", description="")
public class SysDictInfoVo extends BaseVo<SysDictInfoVo> {

    private Long dictId;

    private String appId;

    private String dictField;

    private String dictName;

    private Integer dictKey;

    private String dictValue;

    private Integer dictSortNo;

}
