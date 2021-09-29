//package com.pf.model;
//
//import com.fasterxml.jackson.annotation.JsonCreator;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import org.springframework.security.core.session.SessionInformation;
//
//import java.util.Date;
//
///**
// * @ClassName : MySessionInformation
// * redis存储时，jackson反序列化问题，需要继承下，并添加无参构造函数
// * @Description :
// * @Author : wangjie
// * @Date: 2021/8/17-11:48
// */
//public class MySessionInformation extends SessionInformation {
//    
//    @JsonCreator
//    public MySessionInformation(
//            @JsonProperty("principal") Object principal,
//            @JsonProperty("sessionId") String sessionId,
//            @JsonProperty("lastRequest") Date lastRequest) {
//        super(principal, sessionId, lastRequest);
//    }
//}
