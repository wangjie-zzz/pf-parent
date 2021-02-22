package com.pf.plat.aop;

import com.pf.enums.SysStatusCode;
import com.pf.plat.annotation.Signature;
import com.pf.plat.constants.PlatGeneralConsts;
import com.pf.plat.util.SignatureUtil;
import com.pf.util.Asserts;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
@Aspect
public class SignatureAspect {

    @Autowired
    private RedisTemplate redisTemplate;
	/**
     * - 切面
     */
    private static final String SIGN_CUT = "execution(* com.pf.plat.controller..*(..)) && ( @annotation(com.pf.plat.annotation.Signature) || @within(com.pf.plat.annotation.Signature) )";

    @Pointcut(SIGN_CUT)
    private void pointcut() {}

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) throws Throwable, IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String oldSign = request.getHeader(PlatGeneralConsts.Signature.SIGN_HEADER);
        if (StringUtils.isBlank(oldSign)) { Asserts.fail(SysStatusCode.SIGNATURE_ERROR); }

        // 获取parameters（对应@RequestParam）
        Map<String, String[]> params = null;
        if (!CollectionUtils.isEmpty(request.getParameterMap())) {
            params = request.getParameterMap();
        }

        // 获取path variable（对应@PathVariable）
        String[] paths = null;
        Map<String, String> pathVariables = (Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        if (!CollectionUtils.isEmpty(pathVariables)) { paths = pathVariables.values().toArray(new String[]{}); }

        // 获取body（对应@RequestBody）
        /*if (request instanceof BodyReaderHttpServletRequestWrapper) {
            body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        }*/
        String body = "";
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            body += arg.toString();
        }

        // 获取签名注解元数据
        Signature signature = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(Signature.class);
        if( signature == null ) { signature = AnnotationUtils.findAnnotation(joinPoint.getTarget().getClass(),Signature.class); }

        // 签名必填参数校验
        this.signatureParameterValid(params);
        // 有效期校验
        String timestamp = params.get(PlatGeneralConsts.Signature.TIMESTAMP_QUERY)[0];
        this.timestampExpireValid(timestamp);

        // 请求唯一性校验，防篡改
        String nonce = params.get(PlatGeneralConsts.Signature.NONCE_QUERY)[0];
        String appId = params.get(PlatGeneralConsts.Signature.APP_ID_KEY)[0];
        this.nonceValid(appId,nonce);

        String newSign = new SignatureUtil.Builder(PlatGeneralConsts.Signature.DEFAULT_APP_SECRET).body(body).params(params).paths(paths).build().sign();
        if (!newSign.equals(oldSign)) {
        	Asserts.fail(SysStatusCode.SIGNATURE_ERROR);
        }
    }


    private void signatureParameterValid(Map<String, String[]> map) {
    	if(
    	        !( map.containsKey(PlatGeneralConsts.Signature.APP_ID_KEY) && StringUtils.isNoneBlank(map.get(PlatGeneralConsts.Signature.APP_ID_KEY))
    			&& map.containsKey(PlatGeneralConsts.Signature.TIMESTAMP_QUERY) && StringUtils.isNoneBlank(map.get(PlatGeneralConsts.Signature.TIMESTAMP_QUERY))
    			&& map.containsKey(PlatGeneralConsts.Signature.NONCE_QUERY) && StringUtils.isNoneBlank(map.get(PlatGeneralConsts.Signature.NONCE_QUERY)) )
        ) {
    		Asserts.fail(SysStatusCode.SIGNATURE_PARAMETER_MISSING);
    	}
    }

    public void timestampExpireValid(String timestamp) {
    	Long timestampTp = Long.valueOf(timestamp);
	    // timestamp精确到秒，长度是10位
	    if( timestamp.length() != 10){
	    	Asserts.fail(SysStatusCode.SIGNATURE_TIMESTAMP_LENGTH_ERROE);
	    }
	    Long nowTimestamp = System.currentTimeMillis()/1000;
	    Long startTimestamp = nowTimestamp - 300;
	    Long endTimestamp = nowTimestamp + 300;

	    if(timestampTp < startTimestamp || timestampTp > endTimestamp){
	    	Asserts.fail(SysStatusCode.SIGNATURE_TIMESTAMP_VALIDDATE_TIME_OUT);
	    }
	}

    public void nonceValid(String appId, String nonce) {
        if(redisTemplate.hasKey(appId + nonce)){
            Asserts.fail(SysStatusCode.SIGNATURE_SIGN_INVALID);
        }
    	redisTemplate.opsForValue().set(appId + nonce, appId+ nonce, 10 * 60);
    }
}
