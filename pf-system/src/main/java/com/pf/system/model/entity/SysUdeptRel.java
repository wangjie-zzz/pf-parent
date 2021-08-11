package com.pf.system.model.entity;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUdeptRel对象", description="")
public class SysUdeptRel implements Serializable {

    private static final long serialVersionUID=1L;

    private Long userId;

    private Long deptId;

    private Integer isMain;


}
