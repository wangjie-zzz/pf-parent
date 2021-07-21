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
@ApiModel(value="SysTreedictInfo对象", description="")
public class SysTreedictInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String treedictId;

    private String appId;

    private String treedictField;

    private String treedictName;

    private String treedictKey;

    private String treedictValue;

    private String treedictSupId;

    private String treedictLevel;

    private String treedictIsLeaf;

    private Integer treedictSortNo;

    private String treedictUseState;

    private String treedictIntUser;

    private LocalDateTime treedictIntDate;

    private String treedictUpdUser;

    private LocalDateTime treedictUpdDate;


}
