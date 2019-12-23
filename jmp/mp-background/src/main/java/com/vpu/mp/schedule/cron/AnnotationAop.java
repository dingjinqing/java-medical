package com.vpu.mp.schedule.cron;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author liufei
 * @date 12/22/19
 */
@Aspect
@Component
public class AnnotationAop {
    @Autowired
    CronTaskRegistrar registrar;

    @Before("execution(* CronRunnable.run(..))")
    public void logBeforeV1(JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        byte num = registrar.findByCronKey(className).getRetriesNum();
        modifyAnnotation(CronRunnable.class, Retryable.class, "run", "maxAttempts", String.valueOf(num));
    }

    public Annotation modifyAnnotation(Class className, Class annotationName, String methodName, String modifyField, String paramName, Class<?>... paramTypes) {
        try {
            Method method = className.getDeclaredMethod(methodName, paramTypes);
            Annotation annotation = method.getAnnotation(annotationName);
            InvocationHandler invocationHandler = Proxy.getInvocationHandler(annotation);
            Field memberValues = invocationHandler.getClass().getDeclaredField("memberValues");
            memberValues.setAccessible(true);
            Map<String, Object> values = (Map<String, Object>) memberValues.get(invocationHandler);
            System.out.println("maxAttempts当前值为：" + values.get("maxAttempts"));
            values.put(modifyField, paramName);
            return annotation;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
