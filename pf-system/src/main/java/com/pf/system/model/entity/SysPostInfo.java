package com.pf.system.model.entity;

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
@ApiModel(value="SysPostInfo对象", description="")
public class SysPostInfo implements Serializable {

    private static final long serialVersionUID=1L;

    private Long postId;

    private String postName;

    private Long postTenId;

    private Integer postType;

    private Integer postUseState;

    private Long postIntUser;

    private LocalDateTime postIntDate;

    private Long postUpdUser;

    private LocalDateTime postUpdDate;


}
