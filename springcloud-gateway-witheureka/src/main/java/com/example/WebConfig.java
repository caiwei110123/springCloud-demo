//package com.example;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.support.HandlerMethodArgumentResolver;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import javax.annotation.Resource;
//import java.util.List;
//
//@EnableWebMvc
//@Configuration
//@ComponentScan(basePackages = {"com.example"},useDefaultFilters = true)
//public class WebConfig extends WebMvcConfigurerAdapter {
//    @Resource
//    GlobalInterceptor globalInterceptor;
////    @Autowired
////    SessionParamResolver sessionParamResolver;
//
////    @Resource
////    TestInterceptor testInterceptor;
//
////    @Bean(initMethod = "init")
////    public CsrfTokenRepository csrfTokenRepository(@Value("CSRF") String parameterName,@Value("X-XSRF-TOKEN") String headerName,@Value("60000") int expireTime,@Value("267") int nameSpace){
////        CsrfTokenRepository repository = new CsrfTokenRepository();
////        repository.setHeaderName(headerName);
////        repository.setParameterName(parameterName);
////        return repository;
////    }
////    @Override
////    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
////        System.out.println("WebConfig>>>addArgumentResolvers");
////        argumentResolvers.add(sessionParamResolver);
////    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        System.out.println("WebConfig>>>addInterceptors");
////        registry.addInterceptor(testInterceptor);
//
//        registry.addInterceptor(globalInterceptor);
//    }
//}
