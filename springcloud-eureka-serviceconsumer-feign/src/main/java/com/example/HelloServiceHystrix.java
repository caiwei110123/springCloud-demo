package com.example;

import org.springframework.stereotype.Component;

/**
 * Hystrix的fallback配置
 */
@Component
public class HelloServiceHystrix implements HelloService {

    /**
     * 这里方法声明要与HelloService中的一致
     * @param name
     * @return
     */
    @Override
    public String hi(String name) {
        System.out.println("feign hi   fall back    "+ name);
        return " fall back. hi   " + name;
    }

    @Override
    public String test(String name) {
        System.out.println("feign  test  fall back   "+ name);

        return " fall back.  test  " + name;
    }
}