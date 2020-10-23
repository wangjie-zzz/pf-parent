package com.pf.auth.component.exception;

import com.pf.base.CommonResult;
import com.pf.util.JacksonsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName : DefineAccessDeniedHandler
 * @Description : 匿名用户访问无权限资源时的异常
 */
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/**
	* @Title: commence
	* @Param: [request, response, authException]
	* @description: 匿名用户访问无权限资源时的异常
	* @return: void
	* @throws:
	*/
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		log.info("\r\n{}", authException);
		CommonResult restfulResponse = CommonResult.unauthorized(authException.getMessage());
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(JacksonsUtils.writeValueAsString(restfulResponse));
	}

}
