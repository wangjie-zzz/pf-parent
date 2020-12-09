package com.pf.base;

import com.pf.base.BaseErrCode;

/**
 * 自定义API异常
 * Created by  on 2020/2/27.
 */
public class ApiException extends RuntimeException {
    private BaseErrCode errorCode;

    public ApiException(BaseErrCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseErrCode getErrorCode() {
        return errorCode;
    }
}
