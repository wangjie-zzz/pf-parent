package com.pf.system.component;

import com.pf.base.CommonResult;
import com.pf.constant.SysGeneralConsts;
import com.pf.enums.SysStatusCode;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 
 * @功能描述：日志及响应状态处理
 * @修改日志：
 */
@Component
@Aspect
@Slf4j
@Order(1)
public class RestfulResponseAspect {

    /**
     * 切面
     */
    private static final String POINT_CUT = "execution(* com.pf.system.controller..*(..))";
    @Pointcut(POINT_CUT)
    private void pointcut() {}
	/**
	 * 
	 * @Title: resetResponseMetaStatusCode
	 * @Description: 当出现'服务端异常, 请稍后再试'的错误时，视为非业务逻辑性异常，修改其状态码让上游服务捕获
	 * @modifyLog：
	 */
    @AfterReturning(returning = "object", pointcut = "pointcut()")
    public void resetResponseMetaStatusCode(Object object) throws Throwable {
    	if( object instanceof CommonResult) {
	        CommonResult commonResult =(CommonResult) object;
	        if (commonResult.getCode() == SysStatusCode.FAILED.getCode() ) {
            	log.info("\r\n{}",commonResult.getMessage());
                ServletRequestAttributes res = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                res.getResponse().setStatus(HttpStatus.BAD_REQUEST.value());
            }
       }
    }
}
