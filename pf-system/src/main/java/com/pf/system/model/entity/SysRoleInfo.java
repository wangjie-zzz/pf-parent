package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.enums.UseStateEnum;
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
@ApiModel(value="SysRoleInfo对象", description="")
public class SysRoleInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String roleId;

    private String roleTenId;

    private String roleName;

    private String roleType;

    private String roleUseState;

    private String roleIntUser;

    private LocalDateTime roleIntDate;

    private String roleUpdUser;

    private LocalDateTime roleUpdDate;

    @TableField(exist = false)
    private List<SysRoleAuth> authList;
    @TableField(exist = false)
    private List<SysRoleRel> relList;
}
