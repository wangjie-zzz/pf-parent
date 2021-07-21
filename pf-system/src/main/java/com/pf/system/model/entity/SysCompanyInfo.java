package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
@ApiModel(value="SysCompanyInfo对象", description="")
public class SysCompanyInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String comId;

    private Integer comLevel;

    private String comName;

    private String comTenId;

    private String comSupComId;

    private String comTelMan;

    private String comOrgPhone;

    private String comAddress;

    private String comUseState;

    private String comIntUser;

    private LocalDateTime comIntDate;

    private String comUpdUser;

    private LocalDateTime comUpdDate;

    @TableField(exist = false)
    private List<SysDeptInfo> sysDeptInfos;

}
