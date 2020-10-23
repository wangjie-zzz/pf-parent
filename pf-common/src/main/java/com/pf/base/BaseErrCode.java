package com.pf.base;

/**
 * 封装API的错误码
 * Created by  on 2019/4/19.
 */
public interface BaseErrCode {
    Integer getCode();

    String getMessage();

    String getCodeToStr();
}
