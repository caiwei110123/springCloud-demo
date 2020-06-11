package com.example;


import io.lettuce.core.Consumer;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthSignatureFilter implements GlobalFilter, Ordered {


    /**
     * 全局过滤器 核心方法
     * @param exchange
     * @param chain
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getQueryParams().getFirst("Authroization");
        //向headers中放文件，记得build
        ServerHttpRequest request = exchange.getRequest().mutate().header("X-User", "guanguan").build();
        //将现在的request 变成 exchange对象
        return chain.filter(exchange.mutate().request(request).build());
    }

    @Override
    public int getOrder() {
        return 2;
    }
}