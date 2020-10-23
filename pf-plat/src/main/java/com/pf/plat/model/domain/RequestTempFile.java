package com.pf.plat.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@ApiModel(value = "RequestTempFile", description = "请求临时对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestTempFile {
	
	@ApiModelProperty(value = "文件参数名称", required = true)
	private String fileParameterName;
	
	@ApiModelProperty(value = "文件", required = true)
	MultipartFile[] files = new MultipartFile[] {};
}
