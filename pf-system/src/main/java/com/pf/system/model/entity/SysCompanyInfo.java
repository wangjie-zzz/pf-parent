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
@ApiModel(value="SysCompanyInfo对象", description="")
public class SysCompanyInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private String comId;

    private Integer comLevel;

    private String comName;

    private String tenId;

    private String comSupComId;

    private String comTelMan;

    private String comOrgPhone;

    private String comProviceNo;

    private String comCityNo;

    private String comCutNo;

    private String comAddress;

    private String comDiIngo;

    private String comOngInfo;

    private Integer comSortNo;

    private String comUserState;

    private String comIntUser;

    private LocalDateTime comIntDate;

    private String comUpdUser;

    private LocalDateTime comUpdDate;


}
