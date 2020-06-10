package com.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
@EnableEurekaClient //启用EurekaClient服务
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    private static final Logger LOG = Logger.getLogger(Application.class.getName());




}