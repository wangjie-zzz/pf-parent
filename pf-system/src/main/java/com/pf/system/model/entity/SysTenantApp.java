package com.pf.system.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@ApiModel(value="SysTenantApp对象", description="")
public class SysTenantApp implements Serializable {

    private static final long serialVersionUID=1L;

    private String appId;

    private Long tenId;


}
