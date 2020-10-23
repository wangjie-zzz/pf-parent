package com.pf.plat.model.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "ResponseTempFile", description = "响应临时对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTempFile {

	@ApiModelProperty(value = "file对象", required = true)
	private byte[] fileBytes;
	
	@ApiModelProperty(value = "文件全名", required = true)
	private String filename;
	
	@ApiModelProperty(value = "基础名", required = true)
	private String baseName;
	
	@ApiModelProperty(value = "扩展名", required = true)
	private String extension;
	
}
