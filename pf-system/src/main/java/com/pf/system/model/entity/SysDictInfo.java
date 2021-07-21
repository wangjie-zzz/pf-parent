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
 * @author wangjie
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysDictInfo对象", description="")
public class SysDictInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String dictId;

    private String appId;

    private String dictField;

    private String dictName;

    private String dictKey;

    private String dictValue;

    private Integer dictSortNo;

    private String dictUseState;

    private String dictIntUser;

    private LocalDateTime dictIntDate;

    private String dictUpdUser;

    private LocalDateTime dictUpdDate;

}
