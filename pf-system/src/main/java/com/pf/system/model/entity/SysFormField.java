package com.pf.system.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.pf.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
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
 * @since 2021-10-14
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysFormField对象", description="")
public class SysFormField extends BaseEntity<SysFormField> {
    
    @TableId
    private Long fieldId;
    private String appId;

    private Long formId;

    private String prop;

    @ApiModelProperty(value = "/* right/left/top */字典")
    private Integer type;

    @ApiModelProperty(value = "默认值")
    private String value;

    @ApiModelProperty(value = "/*禁用表单项 false*/")
    private String dict;

    @ApiModelProperty(value = " /*rules属性改变后立即触发一次验证 true*/")
    private Integer clearable;

    @ApiModelProperty(value = "/*必填项隐藏红色星号 false*/")
    private Integer multiple;

    @ApiModelProperty(value = "/*显示校验错误信息 true*/")
    private Integer allowCreate;

    @ApiModelProperty(value = "/*inline形式显示校验信息 false*/")
    private Integer filterable;

    @ApiModelProperty(value = " /*在输入框中显示校验结果反馈图标 false*/")
    private Integer remote;

    private Integer collapseTags;

    private Integer showAllLevels;

    private String label;

    private String labelWidth;

    private Integer spanCol;

    private Integer disable;

    private Integer hidden;

    private String placeholder;

    private Integer required;

    private Integer showMessage;

    private Integer inlineMessage;

    private Integer showPassword;

    private String prefix;

    private String suffix;

    private String prepend;

    private String append;

}
