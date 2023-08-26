package com.hetu.openapigateway;

import com.hetu.common.model.entity.InterfaceInfo;
import com.hetu.common.model.entity.User;
import com.hetu.common.service.InnerInterfaceInfoService;
import com.hetu.common.service.InnerUserInterfaceInfoService;
import com.hetu.common.service.InnerUserService;
import com.hetu.openapiclientsdk.utils.SignUtils;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;


/***
 * @author 河荼
 * @Description 网关全局请求过滤器
 * @Date 11:24 2023/8/23
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;


    // dev阶段仅限本地访问
    private static final List<String> IP_WHINE_LIST = Collections.singletonList("127.0.0.1");

    private static final String INTERFACE_HOST="http://localhost:8123";

    /**
     * 过滤器
     *
     * @param exchange 路由交换机
     * @param chain    责任链
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1.用户发送请求到API网关
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        // 2.请求日志

        final String path = INTERFACE_HOST + request.getPath().value();
        final String method = request.getMethod().toString();
        log.info("================Custom Global Filter Start================");
        log.info("请求ID:{}", request.getId());
        log.info("请求路径:{}", path);
        log.info("请求方法:{}", method);
        log.info("请求参数:{}", request.getQueryParams());
        log.info("请求来源地址:{}", Objects.requireNonNull(request.getLocalAddress()).getHostString());
        log.info("请求来源地址:{}", request.getRemoteAddress());
        log.info("当前时间:{}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        // 3.黑白名单
        String sourceAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        if (!IP_WHINE_LIST.contains(sourceAddress)) {
            log.info("非法请求,requestId:{}", request.getId());
            return handleNoAuth(response);
        }
        // 4.用户鉴权
        HttpHeaders headers = request.getHeaders();

        String accessKey = headers.getFirst("accessKey");
//        String secretKey = headers.getFirst("secretKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String body = headers.getFirst("body");
        String sign = headers.getFirst("sign");

        User invokeUser = null;
        try {
            invokeUser = innerUserService.getInvokeUser(accessKey);
        } catch (Exception e) {
            log.error("请求用户不存在");
        }

        if (invokeUser == null) {
            return handleNoAuth(response);
        }

        if (Long.parseLong(nonce) > 9999) {
            return handleNoAuth(response);
        }
        if (System.currentTimeMillis() / 1000 - Long.parseLong(timestamp) > 60 * 5L) {
            return handleNoAuth(response);
        }
        //用accessKey查询到secretKey
        String secretKey = invokeUser.getSecretKey();
        String severSign = SignUtils.genSign(body, secretKey);

        if (severSign == null || !severSign.equals(sign)) {
            return handleNoAuth(response);
        }

        // 5.请求模拟的接口是否存在
        InterfaceInfo InvokeInterfaceInfo = null;
        try {
            InvokeInterfaceInfo = innerInterfaceInfoService.getInterfaceInfo(path, method);
        } catch (Exception e) {
            log.error("请求模拟接口不存在");
        }
        if (InvokeInterfaceInfo == null) {
            return handleNoAuth(response);
        }
        //5.5 是否剩余次数
        Boolean enuNum = null;
        try {
            enuNum = !innerUserInterfaceInfoService.checkLeftNum(InvokeInterfaceInfo.getId(),invokeUser.getId());
        } catch (Exception e) {
            log.error("剩余次数不足,userID:{},interfaceID:{}",invokeUser.getId(),InvokeInterfaceInfo.getId());
        }
        if (enuNum == null){
            return handleNoEnuNum(response);
        }
        // 6.请求转发,调用模拟接口,响应日志
        log.info("响应状态码:{}", response.getStatusCode());
        log.info("================Custom Global Filter End================");
        return handleResponse(exchange, chain,InvokeInterfaceInfo.getId(),invokeUser.getId());
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,Long interfaceId,Long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            HttpStatus statusCode = originalResponse.getStatusCode();

            if (statusCode == HttpStatus.OK) {
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        //log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                //接口调用次数+1
                                try {
                                    innerUserInterfaceInfoService.invokeCount(interfaceId,userId);
                                } catch (Exception e) {
                                    log.error("接口调用次数更新一异常");
                                }
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);//释放掉内存
                                // 构建日志
                                StringBuilder sb2 = new StringBuilder(200);
                                List<Object> rspArgs = new ArrayList<>();
                                rspArgs.add(originalResponse.getStatusCode());
                                //rspArgs.add(requestUrl);
                                String data = new String(content, StandardCharsets.UTF_8);//data
                                sb2.append(data);
                                log.info("响应数据:{}", data);
                                return bufferFactory.wrap(content);
                            }));
                        } else {
                            //9.调用失败,返回一个规范的错误代码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange);//降级处理返回数据
        } catch (Exception e) {
            log.error("Gateway Handle Exception.\n" + e);
            return chain.filter(exchange);
        }
    }


    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    private Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
    private Mono<Void> handleNoEnuNum(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }
}