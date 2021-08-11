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
@ApiModel(value="SysRoleRel对象", description="")
public class SysRoleRel implements Serializable {

    private static final long serialVersionUID=1L;

    private Long roleId;

    private Long relId;

    private Integer relType;


}
