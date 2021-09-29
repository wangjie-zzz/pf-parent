package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
@ApiModel(value="SysAppInfo对象", description="")
public class SysAppInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private String appId;

    private String appName;

    private Integer appType;

    private String appUrl;

    private String appIcon;

    private String appDesc;

    private String appState;

    private Integer appUseState;

    private Long appIntUser;

    private LocalDateTime appIntDate;

    private Long appUpdUser;

    private LocalDateTime appUpdDate;

    @TableField(exist = false)
    private String appActiveRule;
    @TableField(exist = false)
    private List<SysMenuInfo> sysMenuInfos;

}
