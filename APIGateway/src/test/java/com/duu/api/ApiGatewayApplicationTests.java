package com.duu.api;

import com.duu.api.rpc.RpcDemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@EnableDubbo
@SpringBootTest
class ApiGatewayApplicationTests {

    @DubboReference
    private RpcDemoService rpcDemoService;

    @Test
    void contextLoads() {
        String hello = rpcDemoService.sayHello("duu");
        System.out.println(hello);
    }

}
