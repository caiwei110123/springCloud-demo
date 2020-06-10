package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class HelloController {
    private static final Logger LOG = Logger.getLogger(HelloController.class.getName());

    @Resource
    HelloService helloService;

    @RequestMapping("/hi")
    public String hi(String name) {
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
