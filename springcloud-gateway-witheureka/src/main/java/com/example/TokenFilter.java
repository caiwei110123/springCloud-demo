package com.example;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import io.netty.buffer.ByteBufAllocator;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
@Component
public class TokenFilter implements GlobalFilter, Ordered {
    private static final String AUTHORIZE_TOKEN = "token";
    private static final String AUTHORIZE_UID = "uid";
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 忽略过滤的路径
     */

    @Value("${com.ingoreAuthUrls}")
    private String ignoreAuthUrls;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        System.out.println("TokenFilter>>>filter>>");
//        System.out.println("TokenFilter>>>filter>>");
//        ServerHttpRequest serverHttpRequest = exchange.getRequest();
//        String method = serverHttpRequest.getMethodValue();
//        if ("POST".equals(method)) {
//            System.out.println("TokenFilter>>>filter>>post");
//            //从请求里获取Post请求体
//            String bodyStr = resolveBodyFromRequest(serverHttpRequest);
//            //TODO 得到Post请求的请求参数后，做你想做的事
//
//            //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
//            URI uri = serverHttpRequest.getURI();
//            ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).build();
//            DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
//            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
//
//            request = new ServerHttpRequestDecorator(request) {
//                @Override
//                public Flux<DataBuffer> getBody() {
//                    return bodyFlux;
//                }
//            };
//            //封装request，传给下一级
//            return chain.filter(exchange.mutate().request(request).build());
//        } else if ("GET".equals(method)) {
//            Map requestQueryParams = serverHttpRequest.getQueryParams();
//            //TODO 得到Get请求的请求参数后，做你想做的事
//            System.out.println("TokenFilter>>>filter>>get");
//
//            return chain.filter(exchange);
//        }
//        return chain.filter(exchange);
//
        System.out.println("TokenFilter>>>filter>>");
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String path = request.getURI().getPath();
        if (decideIgnore(path)) {
            return chain.filter(exchange);
        }
        String token = headers.getFirst(AUTHORIZE_TOKEN);
        String uid = headers.getFirst(AUTHORIZE_UID);
        if (token == null) {
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        if (uid == null) {
            uid = request.getQueryParams().getFirst(AUTHORIZE_UID);
        }

        ServerHttpResponse response = exchange.getResponse();
        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        try {
            String authToken = stringRedisTemplate.opsForValue().get(uid);
            if (authToken == null || !authToken.equals(token)) {
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return chain.filter(exchange);





//
//        ServerHttpRequest serverHttpRequest = exchange.getRequest();
//        String method = serverHttpRequest.getMethodValue();
//        if ("POST".equals(method)) {
//            System.out.println("TokenFilter>>>filter>>post");
//            //从请求里获取Post请求体
//            String bodyStr = resolveBodyFromRequest(serverHttpRequest);
//            //TODO 得到Post请求的请求参数后，做你想做的事
//
//            //下面的将请求体再次封装写回到request里，传到下一级，否则，由于请求体已被消费，后续的服务将取不到值
//            URI uri = serverHttpRequest.getURI();
//            ServerHttpRequest request = serverHttpRequest.mutate().uri(uri).build();
//            DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
//            Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
//
//            request = new ServerHttpRequestDecorator(request) {
//                @Override
//                public Flux<DataBuffer> getBody() {
//                    return bodyFlux;
//                }
//            };
//            //封装request，传给下一级
//            return chain.filter(exchange.mutate().request(request).build());
//        } else if ("GET".equals(method)) {
//            Map requestQueryParams = serverHttpRequest.getQueryParams();
//            //TODO 得到Get请求的请求参数后，做你想做的事
//            System.out.println("TokenFilter>>>filter>>get");
//
//            return chain.filter(exchange);
//        }
//        return chain.filter(exchange);
    }
    /**
     * 判断是否是忽略路径
     *
     //     * @param servletPath
     * @return
     */
    private boolean decideIgnore(String servletPath) {
        //跳过不需要验证的路径
        String[] ignoreUrl = this.ignoreAuthUrls.split(",");
        for (String ignore : ignoreUrl) {
            if (ignore.equals(servletPath) || ignore.endsWith("**") && servletPath.contains(ignore.substring(0, ignore.length() - 2))) {
                return true;
            }
        }
        return false;
    }
    /**
     * 从Flux<DataBuffer>中获取字符串的方法
     * @return 请求体
     */
    private String resolveBodyFromRequest(ServerHttpRequest serverHttpRequest) {
        //获取请求体
        Flux<DataBuffer> body = serverHttpRequest.getBody();

        AtomicReference<String> bodyRef = new AtomicReference<>();
        body.subscribe(buffer -> {
            CharBuffer charBuffer = StandardCharsets.UTF_8.decode(buffer.asByteBuffer());
            DataBufferUtils.release(buffer);
            bodyRef.set(charBuffer.toString());
        });
        //获取request body
        return bodyRef.get();
    }

    private DataBuffer stringBuffer(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

        NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(ByteBufAllocator.DEFAULT);
        DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
        buffer.write(bytes);
        return buffer;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
