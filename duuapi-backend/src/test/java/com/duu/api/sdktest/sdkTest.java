package com.duu.api.sdktest;


import com.duu.apiclientsdk.DuuApiClient;
import com.duu.apiclientsdk.DuuApiClientConfig;
import com.duu.apiclientsdk.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author : duu
 * @data : 2023/11/22
 * @from ：https://github.com/0oHo0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class sdkTest {
    @Resource
    DuuApiClient duuApiClient;

    @Test
    public void contextLoads() {
//        String result = duuApiClient.getNameByGet("duu");
//        String result2 = duuApiClient.getNameByPost("duu");
        User user  = new User();
        user.setUserName("duu");
        String result3 = duuApiClient.getUsernameByPost(user);
//        System.out.println(result);
//        System.out.println(result2);
//        System.out.println(result3);
    }
}
