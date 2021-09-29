package com.pf.aop;

import com.pf.base.CommonResult;
import com.pf.base.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 * Created by  on 2020/2/27.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult handle(Exception e) {
        log.error(e.getMessage(), e);
        if(e instanceof ApiException) {
            if (((ApiException) e).getErrorCode() != null) {
                return CommonResult.failed(((ApiException) e).getErrorCode());
            }
        } else if (e instanceof MethodArgumentNotValidException || e instanceof BindException)  {
            BindingResult bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
            String message = null;
            if (bindingResult.hasErrors()) {
                FieldError fieldError = bindingResult.getFieldError();
                if (fieldError != null) {
                    message = fieldError.getField()+fieldError.getDefaultMessage();
                }
            }
            return CommonResult.validateFailed(message);
        }
        return CommonResult.failed(e.getMessage());
    }
}
