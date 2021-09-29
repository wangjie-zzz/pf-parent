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
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysDictInfo对象", description="")
public class SysDictInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long dictId;

    private String appId;

    private String dictField;

    private String dictName;

    private String dictKey;

    private String dictValue;

    private Integer dictSortNo;

    private Integer dictUseState;

    private Long dictIntUser;

    private LocalDateTime dictIntDate;

    private Long dictUpdUser;

    private LocalDateTime dictUpdDate;


}
