package com.duu.api.rpc;

import com.duu.api.rpc.RpcDemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;

/**
 * @author : duu
 * @data : 2023/11/25
 * @from ：https://github.com/0oHo0
 **/
@DubboService
public class RpcDemoServiceImpl implements RpcDemoService {

    @Override
    public String sayHello(String name) {
        System.out.println("Hello " + name + ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name;
    }
}
