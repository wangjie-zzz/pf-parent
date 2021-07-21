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
@ApiModel(value="SysDeptInfo对象", description="")
public class SysDeptInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String deptId;

    private String deptName;

    private String deptComId;

    private String deptTenId;

    private Integer deptLevel;

    private String deptSupDeptId;

    private String deptManager;

    private String deptUseState;

    private String deptIntUser;

    private LocalDateTime deptIntDate;

    private String deptUpdUser;

    private LocalDateTime deptUpdDate;

}
