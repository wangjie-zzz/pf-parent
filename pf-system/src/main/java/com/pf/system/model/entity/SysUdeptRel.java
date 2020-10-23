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
 * @author
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUdeptRel对象", description="")
public class SysUdeptRel implements Serializable {

    private static final long serialVersionUID=1L;

    private String userId;

    private String deptId;

    private String isMain;


}
