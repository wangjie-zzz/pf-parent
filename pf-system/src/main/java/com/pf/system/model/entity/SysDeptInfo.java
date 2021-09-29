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
@ApiModel(value="SysDeptInfo对象", description="")
public class SysDeptInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long deptId;

    private String deptName;

    private Long deptTenId;

    private Long deptComId;

    private Integer deptLevel;

    private Long deptSupDeptId;

    private Long deptManager;

    private Integer deptUseState;

    private Long deptIntUser;

    private LocalDateTime deptIntDate;

    private Long deptUpdUser;

    private LocalDateTime deptUpdDate;


}
