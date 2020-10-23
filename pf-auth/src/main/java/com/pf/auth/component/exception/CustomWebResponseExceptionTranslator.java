package com.pf.auth.component.exception;

import com.pf.base.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/***
* @Title:
* @Param:
* @description: 登录时异常处理器
* @return:
* @throws:
*/
@Slf4j
public class CustomWebResponseExceptionTranslator extends DefaultWebResponseExceptionTranslator {

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(responseEntity.getHeaders().toSingleValueMap());
        log.error("\r\n CustomWebResponseExceptionTranslator");
        CommonResult restfulResponse = CommonResult.failed(e.getMessage());
        return new ResponseEntity(restfulResponse, headers, HttpStatus.OK);
	}

}
