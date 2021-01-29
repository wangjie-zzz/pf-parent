package com.pf.plat.exchange.socket;

import com.alibaba.fastjson.JSONObject;
import com.pf.bean.SnowflakeIdWorker;
import com.pf.enums.DataFormatsEnum;
import com.pf.plat.exchange.socket.config.ExponentialBackOffRetry;

/**
 * @ClassName : TestMain
 * @Description : 
 * @Author : wangjie
 * @Date: 2020/12/14-16:50
 */
public class TestMain {
    public static void main(String[] args) throws Exception {

        startServe();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("init", "init info");
        JSONObject request = new JSONObject();
        request.put("request", "request info");
        JSONObject response = new JSONObject();
        response.put("response", "response info");
        SocketClient client = SocketClient.builder()
                .connType(SocketClient.LONG_CONN)
                .charsets("utf-8")
                .heartbeatRequest(request.toJSONString())
                .heartbeatResponse(response.toJSONString())
                .ssl(true)
                .initResData(jsonObject.toJSONString())
                .requestDataFormatsEnum(DataFormatsEnum.JSON)
                .responseDataFormatsEnum(DataFormatsEnum.JSON)
                .retryPolicy(
                        new ExponentialBackOffRetry(1000, 5, 1000))
                .build();
        client.init();

        Thread.sleep(5000); // 长连接异步
        JSONObject test = new JSONObject();
        test.put("id", SnowflakeIdWorker.getInstance().nextIdString());
        int i=10;
        while (i>0) {
            i--;
            client.sendData(test.toJSONString());
        }
    }
    public static void startServe() throws Exception {
        JsonServer jsonServer = new JsonServer(true, 8888);
        jsonServer.start();
    }
}
