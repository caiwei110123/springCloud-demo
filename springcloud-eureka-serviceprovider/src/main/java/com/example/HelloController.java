package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
@RestController
public class HelloController {
    @Value("${server.port}")
    String port;

    @RequestMapping("/hi")
    public String hi(String name) {
        System.out.println("provider : name=" + name);
        return String.format("hello hi method  %s , from port=%s", name, port);
    }
    @RequestMapping("/test")
    public String test(String name) {
        System.out.println("provider>>>test>>>name=" + name);
        return String.format("this is test method %s , from port=%s", name, port);
    }

    @RequestMapping("/hello/hi")
    public String hello(String name) {
        return String.format("hello %s , from port=%s", name, port);
    }

}
