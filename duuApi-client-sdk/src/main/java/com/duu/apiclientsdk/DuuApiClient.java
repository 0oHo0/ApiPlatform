package com.duu.apiclientsdk;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.duu.apiclientsdk.SignUtils.getSign;

/**
 * @author : duu
 * @data : 2023/11/22
 * @from ：https://github.com/0oHo0
 **/

public class DuuApiClient {

    private String GATEWAY_ADDRESS="http://127.0.0.1:8090";

    private String accessKey;

    private String secretKey;

    public DuuApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result = HttpUtil.get(GATEWAY_ADDRESS+"/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result = HttpUtil.post(GATEWAY_ADDRESS+"/api/name/post", paramMap);
        System.out.println(result);
        return result;
    }
    public String getUsernameByPost(User user) {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse =
                HttpRequest.post(GATEWAY_ADDRESS+"/api/name/user").addHeaders(getHeaderMap(json)).body(json).execute();
        System.out.println(httpResponse.getStatus());
        String result = httpResponse.body();
        System.out.println(result);
        return result;
    }
    private Map<String,String> getHeaderMap(String body ){
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("accessKey",accessKey);
        paramMap.put("nonce", RandomUtil.randomNumbers(4));
        paramMap.put("body",body);
        paramMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        paramMap.put("sign", getSign(body,secretKey));
        return paramMap;
    }
}
