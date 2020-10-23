package com.pf.system.model.request;

import lombok.Builder;
import lombok.Data;

/**
 * @ClassName : LoginRequest
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/9/17-16:58
 */
@Data
@Builder
public class LoginRequest {

    private String type;
    private String pwd;
    private String code;
}
