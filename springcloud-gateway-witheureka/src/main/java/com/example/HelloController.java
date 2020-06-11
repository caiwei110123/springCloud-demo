package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import web.bo.ResponseResult;
import web.bo.SessionInfo;
import web.bo.TokenResponse;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RestController
public class HelloController {

}
