//package com..mall.auth.feign;
//
//import com..mall.base.CommonResult;
//import com..mall.domain.UserDto;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
///**
// * @ClassName : IMallSystemFeign
// * @Description :
// * @Author : wangjie
// * @Date: 2020/9/17-11:27
// */
//@FeignClient(name = IMallSystemFeign.Name)
//public interface IMallSystemFeign {
//
//    public final static String Name = "mall-system/mall-system";
//
//    public final static String API_PREFIX = "/api/v1";
//
//    public final static String User_Controller = "/sysUserInfo";
//
//    @GetMapping(value = IMallSystemFeign.API_PREFIX + IMallSystemFeign.User_Controller + "/selectUserAndRoleInfo")
//    public CommonResult<UserDto> selectUserAndRoleInfo(@RequestParam("userId") String userId);
//
//    @GetMapping(value = IMallSystemFeign.API_PREFIX + IMallSystemFeign.User_Controller + "/selectUserAndRoleInfoBySmsCode")
//    public CommonResult<UserDto> selectUserAndRoleInfoBySmsCode(@RequestParam("code") String code);
//}
