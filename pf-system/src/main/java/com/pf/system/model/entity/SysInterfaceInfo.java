package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value="SysInterfaceInfo对象", description="")
public class SysInterfaceInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String interfaceId;

    private String interfaceCode;

    private String appId;

    private String interfaceUrl;

    private String interfaceName;

    private Integer interfaceSortNo;

    private String interfaceUseState;

    private String interfaceIntUser;

    private LocalDateTime interfaceIntDate;

    private String interfaceUpdUser;

    private LocalDateTime interfaceUpdDate;


}
