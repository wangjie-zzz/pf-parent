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
@ApiModel(value="SysPostInfo对象", description="")
public class SysPostInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private String postId;

    private String postName;

    private String postType;

    private String postTenId;

    private String postUseState;

    private String postIntUser;

    private LocalDateTime postIntDate;

    private String postUpdUser;

    private LocalDateTime postUpdDate;


}
