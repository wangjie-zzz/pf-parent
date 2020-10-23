package com.pf.system.model.entity;

import com.pf.config.SnowflakeIdWorker;
import com.pf.enums.UseStateEnum;
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
@ApiModel(value="SysRoleInfo对象", description="")
public class SysRoleInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private String roleId;

    private String tenId;

    private String deptId;

    private String roleName;

    private String roleType;

    private String roleUserState;

    private String roleIntUser;

    private LocalDateTime roleIntDate;

    private String roleUpdUser;

    private LocalDateTime roleUpdDate;

    public SysRoleInfo updateDefaultInfo(String userId, SysRoleInfo sysRoleInfo) {
        if(sysRoleInfo == null) sysRoleInfo = new SysRoleInfo();
        String roleId = SnowflakeIdWorker.getInstance().nextIdString();
        LocalDateTime now = LocalDateTime.now();
        sysRoleInfo.setRoleId(roleId);
        sysRoleInfo.setRoleIntDate(now);
        sysRoleInfo.setRoleUpdDate(now);
        sysRoleInfo.setRoleIntUser(userId);
        sysRoleInfo.setRoleUpdUser(userId);
        sysRoleInfo.setRoleUserState(UseStateEnum.EFFECTIVE.getCodeToStr());
        return sysRoleInfo;
    }
}
