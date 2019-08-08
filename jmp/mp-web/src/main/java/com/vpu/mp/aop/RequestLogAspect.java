package com.vpu.mp.aop;


import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.util.RequestUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * controller所有请求日志记录
 * @author 卢光耀
 * @date 2019-08-06 14:14
 *
*/
@Slf4j
@Aspect
@Configuration
@ConditionalOnProperty(prefix="local",name = "log", havingValue = "on")
public class RequestLogAspect {
    @Pointcut("execution(public com.vpu.mp.service.foundation.data.JsonResult com.vpu.mp.controller..*Controller.*(..))")
    public void controllerLogAspect(){}

    @Around(value = "controllerLogAspect()")
    public JsonResult beforeRequest(ProceedingJoinPoint point){
        StringBuilder logAfterStr = new StringBuilder();
        JsonResult result;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
            .getRequestAttributes()).getRequest();
        String token = request.getHeader("V-Token");
        String ip = RequestUtil.getIp(request);
        String methodName = point.getSignature().getName();
        logAfterStr.append("\n");
        logAfterStr.append("#####################Request#####################").append("\n");
        logAfterStr.append("MethodName   :").append(methodName).append("\n");
        logAfterStr.append("UserToken    :").append(token).append("\n");
        logAfterStr.append("RequestIP    :").append(ip).append("\n");
        logAfterStr.append("RequestURI   :").append(request.getRequestURI()).append("\n");
        logAfterStr.append("RequestParams:");
        if( point.getArgs() != null && point.getArgs().length > 0){
            Arrays.stream(point.getArgs()).
                filter(o -> !(o instanceof BindingResult)).
                forEach(o -> logAfterStr.append("【"+o+"】"));
            logAfterStr.append("\n");
        }
        log.info(logAfterStr.toString());
        try{
            StringBuilder logResponseStr = new StringBuilder();
            result = (JsonResult) point.proceed();
            logResponseStr.append("\n");
            logResponseStr.append("#####################Response#####################").append("\n");
            logResponseStr.append("MethodName   :").append(methodName).append("\n");
            logResponseStr.append("UserToken    :").append(token).append("\n");
            logResponseStr.append("RequestIP    :").append(ip).append("\n");
            logResponseStr.append("RequestURI   :").append(request.getRequestURI()).append("\n");
            logResponseStr.append("JsonResult   :").append(result);
            log.info(logResponseStr.toString());
        }catch (Throwable throwable){
            StringBuilder logErrorStr = new StringBuilder();
            logErrorStr.append("\n");
            logErrorStr.append("#####################Exception#####################").append("\n");
            logErrorStr.append("MethodName   :").append(methodName).append("\n");
            logErrorStr.append("UserToken    :").append(token).append("\n");
            logErrorStr.append("RequestIP    :").append(ip).append("\n");
            logErrorStr.append("RequestURI   :").append(request.getRequestURI()).append("\n");
            logErrorStr.append("Exception    :").append(throwable);
            log.error(logErrorStr.toString());
            throw new RuntimeException();
        }
        return result;
    }

}
