package com.vpu.mp.service.shop.operation.aop;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 操作记录切面
 * @author: 卢光耀
 * @date: 2019-07-12 09:49
 *
*/
@Aspect
@Configuration
public class RecordAdminActionAspect {
    @Pointcut("@annotation(com.vpu.mp.service.shop.operation.aop.RecordAction)")
    public void recordAspect(){

    }

    @AfterReturning(value = "recordAspect()")
    public void returnRunning(JoinPoint point){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();

        RecordAction recordAction = method.getAnnotation(RecordAction.class);

        System.out.println(recordAction);
        System.out.println(recordAction.templateId());
        if ( attributes != null){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes()).getRequest();
        }else {

        }
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

    }
}
