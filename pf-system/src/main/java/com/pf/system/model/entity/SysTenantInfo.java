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
 * @author
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysTenantInfo对象", description="")
public class SysTenantInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private String tenId;

    private String tenName;

    private String tenUseState;

    private String tenIntUser;

    private LocalDateTime tenIntDate;

    private String tenUpdUser;

    private LocalDateTime tenUpdDate;


}
