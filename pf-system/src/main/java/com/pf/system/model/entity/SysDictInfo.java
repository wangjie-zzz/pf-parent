package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.pf.base.BaseEntity;
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
 * @author pf
 * @since 2021-08-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysDictInfo对象", description="")
public class SysDictInfo extends BaseEntity<SysDictInfo> {

    @TableId
    private Long dictId;

    private String appId;

    private String dictField;

    private String dictName;

    private Integer dictKey;

    private String dictValue;

    private Integer dictSortNo;

}
