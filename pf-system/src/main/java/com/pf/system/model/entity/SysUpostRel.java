package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author
 * @since 2020-09-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysUpostRel对象", description="")
public class SysUpostRel implements Serializable {

    private static final long serialVersionUID=1L;

    private String userId;

    private String postId;

    @TableField(exist = false)
    private String userName;
}
