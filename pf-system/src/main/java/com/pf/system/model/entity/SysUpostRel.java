package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author pf
 * @since 2021-08-11
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUpostRel对象", description="")
public class SysUpostRel implements Serializable {

    public SysUpostRel(Long userId, Long postId) {
        this.userId = userId;
        this.postId = postId;
    }
    private static final long serialVersionUID=1L;

    private Long userId;

    private Long postId;

    @TableField(exist = false)
    private String userName;

}
