//package com.example;
//
//import org.apache.commons.lang.StringUtils;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.PrintWriter;
//@Component
//public class GlobalInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("GlobalInterceptor>>>preHandle------");
//        String secretKey = request.getHeader("from");
//        if(!StringUtils.isNotBlank(secretKey)||secretKey.equals("gateway")){
//            response.setContentType("application/json; charset=utf-8");
//            PrintWriter writer = response.getWriter();
//            writer.write("error");
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
