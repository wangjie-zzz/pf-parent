package com.pf.system.model.dto;

import com.pf.base.BaseDto;
import com.pf.system.model.entity.SysFormInfo;
import com.pf.util.JacksonsUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author pf
 * @since 2021-10-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="SysFormInfo对象", description="")
public class FormInfoDto extends BaseDto<FormInfoDto> {

    private Long formId;
    private String appId;

    private String name;

    @ApiModelProperty(value = "/* right/left/top */字典")
    private Integer labelPosition;

    
    private String labelWidth;

    @ApiModelProperty(value = "/*禁用表单项 false*/")
    private Integer disabled;

    @ApiModelProperty(value = " /*rules属性改变后立即触发一次验证 true*/")
    private Integer validateOnRuleChange;

    @ApiModelProperty(value = "/*必填项隐藏红色星号 false*/")
    private Integer hideRequiredAsterisk;

    @ApiModelProperty(value = "/*显示校验错误信息 true*/")
    private Integer showMessage;

    @ApiModelProperty(value = "/*inline形式显示校验信息 false*/")
    private Integer inlineMessage;

    @ApiModelProperty(value = " /*在输入框中显示校验结果反馈图标 true*/")
    private Integer statusIcon;

    private List<FormFieldDto> fieldDtos;

}
