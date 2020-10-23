package com.pf.auth.component.exception;

import com.pf.base.CommonResult;
import com.pf.util.JacksonsUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName : DefineAccessDeniedHandler
 * @Description : 认证过的用户访问无权限资源时的异常
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  
	/***
	* @Title: handle
	* @Param: [request, response, accessDeniedException]
	* @description: 授权失败(forbidden)时返回信息
	* @return: void
	* @throws:
	*/
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
		CommonResult restfulResponse = CommonResult.forbidden(accessDeniedException.getMessage());
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(JacksonsUtils.writeValueAsString(restfulResponse));
	}

}
