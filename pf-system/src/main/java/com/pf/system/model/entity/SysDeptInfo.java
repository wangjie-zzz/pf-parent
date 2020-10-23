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
@ApiModel(value="SysDeptInfo对象", description="")
public class SysDeptInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private String deptId;

    private String deptName;

    private String comId;

    private String tenId;

    private Integer deptLevel;

    private String deptSupDeptId;

    private String deptType;

    private String deptManager;

    private Integer deptSortNo;

    private String deptUserState;

    private String deptIntUser;

    private LocalDateTime deptIntDate;

    private String deptUpdUser;

    private LocalDateTime deptUpdDate;


}
