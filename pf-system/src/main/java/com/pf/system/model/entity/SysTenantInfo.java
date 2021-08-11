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
@ApiModel(value="SysTenantInfo对象", description="")
public class SysTenantInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long tenId;

    private String tenName;

    private Integer tenUseState;

    private Long tenIntUser;

    private LocalDateTime tenIntDate;

    private Long tenUpdUser;

    private LocalDateTime tenUpdDate;


}
