package com.example;

import annotation.SessionParam;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import web.bo.ResponseResult;
import web.bo.SessionInfo;
import web.bo.TokenResponse;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RestController
public class HelloController {
    private static final Logger LOG = Logger.getLogger(HelloController.class.getName());

    @Resource
    HelloService helloService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult login(@SessionParam SessionInfo sessionInfo, @RequestBody User user) {
        try {
            String token = TokenProcessor.generateTokeCode();
            ValueOperations<String,String> hashOperations = stringRedisTemplate.opsForValue();
            String uid = "666";
            if (Objects.equals(user.getUserName(),"abc")) {
                stringRedisTemplate.delete(uid);
                hashOperations.append(uid,token);
            }
            stringRedisTemplate.delete(user.getUserName());

            hashOperations.append(user.getUserName(),token);

            stringRedisTemplate.expire(user.getUserName(),1000*50, TimeUnit.SECONDS);
            stringRedisTemplate.expire(uid,1000*50, TimeUnit.SECONDS);

            TokenResponse loginResponse = new TokenResponse();
            loginResponse.setToken(token);
            loginResponse.setUserId(user.getUserName());
            loginResponse.setUid(uid);
            sessionInfo.setUserId(user.getUserName());
            return  ResponseResult.success(loginResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  new ResponseResult();

    }
    @RequestMapping("/hi")
    public String hSessionParamResolveri(@SessionParam SessionInfo sessionInfo, String name) {
        System.out.println("feign>>>hi>>>name="+name);
     //   LOG.log(Level.INFO, "calling trace service-hi  ");
        //这里直接写的是服务名： springcloud-eureka-serviceprovider  。在ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用具体的url替换掉服务名
        return helloService.hi(name);
    }

    @RequestMapping("/test")
    public String test(String name) {
        System.out.println("feign>>>test>>>name="+name);
        //   LOG.log(Level.INFO, "calling trace service-hi  ");
        //这里直接写的是服务名： springcloud-eureka-serviceprovider  。在ribbon中它会根据服务名来选择具体的服务实例，根据服务实例在请求的时候会用具体的url替换掉服务名
        return helloService.test(name);
    }
}
