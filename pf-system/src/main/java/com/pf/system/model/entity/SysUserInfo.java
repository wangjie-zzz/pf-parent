package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.enums.UseStateEnum;
import com.pf.util.Asserts;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@ApiModel(value="SysUserInfo对象", description="")
@AllArgsConstructor
@NoArgsConstructor
public class SysUserInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String userId;

    private String userName;

    private String userPasswd;

    private String userCardId;

    private String userSex;

    private LocalDateTime userBirthday;

    private String userHeadImg;

    private String userDeptId;

    private String userComId;

    private String userTenId;

    private String userCode;

    private String userPhone;

    private String userWxOpenId;

    private String userEmail;

    private String userQqOpenId;

    private String userDataSource;

    private String userUseState;

    private String userIntUser;

    private LocalDateTime userIntDate;

    private String userUpdUser;

    private LocalDateTime userUpdDate;

    @TableField(exist = false)
    private List<SysUpostRel> uposts;
    @TableField(exist = false)
    private List<SysRoleInfo> roles;

    public void checkUserUseState() {
        if(UseStateEnum.FROZEN.getCodeToStr().equals(userUseState)) {
            Asserts.fail("该用户已冻结！");
        } else if(UseStateEnum.INVALID.getCodeToStr().equals(userUseState)) {
            Asserts.fail("无效用户！");
        } else if(UseStateEnum.ABOLISH.getCodeToStr().equals(userUseState)) {
            Asserts.fail("无效用户！");
        }
    }
}
