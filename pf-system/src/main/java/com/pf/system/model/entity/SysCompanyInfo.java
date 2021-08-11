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
@ApiModel(value="SysCompanyInfo对象", description="")
public class SysCompanyInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long comId;

    private Integer comLevel;

    private String comName;

    private Long comTenId;

    private Long comSupComId;

    private Long comTelMan;

    private String comOrgPhone;

    private String comAddress;

    private Integer comUseState;

    private Long comIntUser;

    private LocalDateTime comIntDate;

    private Long comUpdUser;

    private LocalDateTime comUpdDate;


}
