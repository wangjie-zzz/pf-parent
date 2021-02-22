package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @author
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysAppInfo对象", description="")
public class SysAppInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String appId;

    private String appName;

    private String appType;

    private String appUrl;

    private String appIcon;

    private String appDesc;

    private String appState;

    private String appUseState;

    private String appIntUser;

    private LocalDateTime appIntDate;

    private String appUpdUser;

    private LocalDateTime appUpdDate;

    @TableField(exist = false)
    private String appActiveRule;

    @TableField(exist = false)
    private List<SysMenuInfo> sysMenuInfos;
}
