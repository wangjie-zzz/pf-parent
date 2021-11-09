package com.pf.system.model.dto;

import com.pf.base.BaseDto;
import com.pf.system.model.entity.SysDictInfo;
import com.pf.util.JacksonsUtils;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
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
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysDictInfo对象", description="")
public class DictInfoDto extends BaseDto<DictInfoDto> {
    
    private Long dictId;

    private String appId;

    private String dictField;

    private String dictName;

    private Integer dictKey;

    private String dictValue;

    private Integer dictSortNo;
}
