package com.pf.test.controller;

import com.pf.annotation.RedisLock;
import com.pf.base.CommonResult;
import com.pf.test.service.ITestAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName : TestAController
 * @Description :
 * @Author : wangjie
 * @Date: 2020/11/5-14:53
 */
@RestController
@RequestMapping(path = "/aop", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class AopTestController {

    @RedisLock
    @PostMapping(path = "/test/{dd}")
    public Map<String, String> testA1(
            String aa,
            @RequestParam("bb") String bb,
            @PathVariable("dd") String dd,
            @RequestBody String cc
    ) throws InterruptedException {
        Thread.sleep(10000);
        Map<String, String> map = new HashMap<>();
        map.put("aa", aa);
        map.put("bb", bb);
        map.put("cc", cc);
        map.put("dd", dd);
        return map;
    }
}
