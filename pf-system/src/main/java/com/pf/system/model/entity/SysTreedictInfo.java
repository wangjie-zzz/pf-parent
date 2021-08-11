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
@ApiModel(value="SysTreedictInfo对象", description="")
public class SysTreedictInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long treedictId;

    private String appId;

    private String treedictField;

    private String treedictName;

    private String treedictKey;

    private String treedictValue;

    private Long treedictSupId;

    private Integer treedictLevel;

    private Integer treedictIsLeaf;

    private Integer treedictSortNo;

    private Integer treedictUseState;

    private Long treedictIntUser;

    private LocalDateTime treedictIntDate;

    private Long treedictUpdUser;

    private LocalDateTime treedictUpdDate;


}
