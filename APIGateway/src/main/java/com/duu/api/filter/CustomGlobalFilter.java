package com.duu.api.filter;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

import com.duu.apiclientsdk.SignUtils;
import com.duu.common.model.entity.InterfaceInfo;
import com.duu.common.model.entity.User;
import com.duu.common.service.InnerInterfaceInfoService;
import com.duu.common.service.InnerUserInterfaceInfoService;
import com.duu.common.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : duu
 * @data : 2023/11/24
 * @from ：https://github.com/0oHo0
 **/
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerinterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    public static final String REMOTE_ADDRESS = "http://localhost:8101";
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1", "127.0.0.2");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求id: {}", request.getId());
        String path = REMOTE_ADDRESS + request.getPath().value();
        log.info("请求路径: {}", path);
        String method = request.getMethod().toString();
        log.info("请求方法: {}", method);
        log.info("请求参数: {}", request.getQueryParams());
        log.info("请求头: {}", request.getHeaders());
        log.info("请求地址: {}", request.getRemoteAddress());
        // 2. 访问控制 - 黑白名单
        if (IP_WHITE_LIST.contains(request.getRemoteAddress())) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        // 3. 用户鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        // 防止中文乱码
        String body = null;
        try {
            body = URLDecoder.decode(headers.getFirst("body"), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String sign = headers.getFirst("sign");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        // boolean hasBlank = StringUtil.b(accessKey, body, sign, nonce, timestamp);
        // 判断是否有空
//        if (hasBlank) {
//            return handleInvokeError(exchange.getResponse());
//        }

        // TODO 使用accessKey去数据库查询secretKey
        User invokeUser = innerUserService.getInvokeUser(accessKey);
        String secretKey = invokeUser.getSecretKey();
        String sign1 = SignUtils.getSign(body, secretKey);
        if (!StrUtil.equals(sign, sign1)) {
            return handleNoAuth(exchange.getResponse());
        }
        // TODO 判断随机数nonce

        // 时间戳是否为数字
        if (!NumberUtil.isNumber(timestamp)) {
            return handleInvokeError(exchange.getResponse());
        }
        // 五分钟内的请求有效
        Long currentTime = System.currentTimeMillis() / 1000;
        Long requestTime = Long.parseLong(timestamp);
        if (currentTime - requestTime > 60 * 5L) {
            return handleNoAuth(exchange.getResponse());
        }
        // 4、判读请求的接口是否存在
        InterfaceInfo interfaceInfo = innerinterfaceInfoService.getInterfaceInfo(path, method);
        Long interfaceId = interfaceInfo.getId();
        Long userId = invokeUser.getId();
        // 5. 请求转发，调用模拟接口
        return handleResponse(exchange, chain,interfaceId, userId);

    }

    @Override
    public int getOrder() {
        return -1;
    }


    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    /**
     * 处理响应
     *
     * @param exchange
     * @param chain
     * @return
     */
    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long interfaceId,long userId) {
        try {
            // 从交换机拿到原始response
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓冲区工厂 拿到缓存数据
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到状态码
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        // 对象是响应式的
                        if (body instanceof Flux) {
                            // 我们拿到真正的body
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里面写数据
                            // 拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                // 调用成功，接口调用次数+1
                                try {
                                    innerUserInterfaceInfoService.invokeCount(interfaceId, userId);
                                } catch (Exception e) {
                                    log.error("接口调用计数失败");
                                }
                                // data从这个content中读取
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);// 释放掉内存
                                // 6.构建日志
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);// data
                                rspArgs.add(data);
                                log.info("<--- status:{} data:{}"// data
                                        , rspArgs.toArray());// log.info("<-- {} {}", originalResponse.getStatusCode
                                // (), data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            // 8.调用失败返回错误状态码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);// 降级处理返回数据
        } catch (Exception e) {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }

    }
}