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
@ApiModel(value="SysRoleInfo对象", description="")
public class SysRoleInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long roleId;

    private String roleName;

    private Long roleTenId;

    private Integer roleType;

    private Integer roleUseState;

    private Long roleIntUser;

    private LocalDateTime roleIntDate;

    private Long roleUpdUser;

    private LocalDateTime roleUpdDate;


}
