package com.pf.util;

import com.google.common.collect.ImmutableList;
import com.pf.enums.DataFormatsEnum;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
* @Title: Http消息头工具类
* @Param:
* @description:
* @author: wangjie
* @date: 2020/10/15 22:08
* @return:
* @throws:
*/
public class HttpHeaderUtil {

    public static String USER_IDENTITY = "user_identity";
    /***
     * @Title: getAuthorization
     * @description: 获取授权消息头
     * @throws:
     */
    public static String getAuthorization(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        return authorization;
    }

    /***
     * @Title: getAccessToken
     * @description: 获取令牌
     * @throws:
     */
    public static String getAccessToken(){
        String authorization = getAuthorization();
        String accessToken = "";
        if( !StringUtils.isEmpty(authorization) && authorization.toUpperCase().startsWith("BEARER") ) {
            String[] arr = authorization.split("\\s+");
            accessToken = arr[1];
        }
        return accessToken;
    }

    /***
    * @Title: getUserIdentity
    * @description:  获取登录用户唯一标识
    * @throws:
    */
    public static String getUserIdentity(){
        return getHeader(USER_IDENTITY);
    }


    /***
     * @Title: getHeader
     * @description:  获取自定义消息头
     * @throws:
     */
    public static String getHeader(String key){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if( requestAttributes == null) {
            return "";
        }
        HttpServletRequest request = requestAttributes.getRequest();
        return request.getHeader(key);
    }


    /**
     * @Description 获取header并转换成实体对象
     * @ModifyLogs
     */
    public static <T> T getHeader(String headKey, Class<T> clazz) {
        String header = getHeader(headKey);
        if( StringUtils.isEmpty(header) ){
            return null;
        }
        T t = null;
        try {
            t = JacksonsUtils.getMapper().readValue(header, clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static HttpHeaders initHttpHeaders(DataFormatsEnum requestDataFormatsEnum, DataFormatsEnum responseDataFormatsEnum) {
        HttpHeaders httpHeaders = new HttpHeaders();
        /*请求类型*/
        switch ( requestDataFormatsEnum ) {
            case FORM_URLENCODED:
                httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                break;
            case FORM_DATA:
                httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
                break;
            case JSON:
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                break;
            default:
                break;

        }
        /*响应类型*/
        switch ( responseDataFormatsEnum ) {
            case FORM_URLENCODED:
                httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_FORM_URLENCODED));
                break;
            case FORM_DATA:
                httpHeaders.setAccept(ImmutableList.of(MediaType.MULTIPART_FORM_DATA));
                break;
            case JSON:
                httpHeaders.setAccept(ImmutableList.of(MediaType.APPLICATION_JSON));
                break;
            case BINARY:
                httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                break;
            default:
                break;
        }
        return httpHeaders;
    }
}
