package com.pf.util;

import com.pf.base.ApiException;
import com.pf.base.BaseErrCode;

/**
 * 断言处理类，用于抛出各种API异常
 * Created by  on 2020/2/27.
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(BaseErrCode errorCode) {
        throw new ApiException(errorCode);
    }
}
