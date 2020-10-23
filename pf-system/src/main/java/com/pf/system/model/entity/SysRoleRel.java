package com.pf.system.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
@ApiModel(value="SysRoleRel对象", description="")
@Builder
public class SysRoleRel implements Serializable {

    private static final long serialVersionUID=1L;

    private String roleId;

    private String relId;

    private String relType;
}
