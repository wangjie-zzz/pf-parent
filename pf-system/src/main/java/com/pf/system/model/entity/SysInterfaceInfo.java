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
@ApiModel(value="SysInterfaceInfo对象", description="")
public class SysInterfaceInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long interfaceId;

    private String interfaceCode;

    private String appId;

    private String interfaceUrl;

    private String interfaceName;

    private Integer interfaceSortNo;

    private Integer interfaceUseState;

    private Long interfaceIntUser;

    private LocalDateTime interfaceIntDate;

    private Long interfaceUpdUser;

    private LocalDateTime interfaceUpdDate;


}
