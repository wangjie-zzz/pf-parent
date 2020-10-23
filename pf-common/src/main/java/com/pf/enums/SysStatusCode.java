package com.pf.enums;

import com.pf.base.BaseErrCode;

/**
 * 枚举了一些常用API操作码
 * Created by  on 2019/4/19.
 */
public enum SysStatusCode implements BaseErrCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    SYS_BUSY(501, "系统繁忙，请稍后重试"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(409, "没有相关权限"),
    USER_CODE_PWD_FAIL(410, "用户名或密码错误"),
    SERVICE_PROVIDER_SIDE_EXCEPTION( 411, "服务提供端异常"),
    SERVICE_CONSUMER_SIDE_EXCEPTION( 412, "服务消费端异常"),


    PLAT_CONN_TYPE_EXCEPTION( 10001, "连接类型错误"),
    PLAT_SOCKET_CLIENT_INIT_ERROR(50056, "socket客户端初始化失败"),
    ;
    private Integer code;
    private String message;

    private SysStatusCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String getCodeToStr() {
        return String.valueOf(code);
    }
}
