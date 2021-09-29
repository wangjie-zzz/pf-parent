package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.pf.enums.UseStateEnum;
import com.pf.util.Asserts;
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
@ApiModel(value="SysUserInfo对象", description="")
public class SysUserInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long userId;

    private String userName;

    private String userPasswd;

    private String userCardId;

    private Integer userSex;

    private LocalDateTime userBirthday;

    private String userHeadImg;

    private Long userDeptId;

    private Long userComId;

    private Long userTenId;

    private String userCode;

    private String userPhone;

    private String userWxOpenId;

    private String userEmail;

    private String userQqOpenId;

    private Integer userDataSource;

    private Integer userUseState;

    private Long userIntUser;

    private LocalDateTime userIntDate;

    private Long userUpdUser;

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
