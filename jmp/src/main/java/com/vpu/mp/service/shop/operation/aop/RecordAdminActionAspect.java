package com.vpu.mp.service.shop.operation.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作记录切面
 * @author: 卢光耀
 * @date: 2019-07-12 09:49
 *
*/
@Aspect
@Component
public class RecordAdminActionAspect {



    @AfterReturning("@annotation(com.vpu.mp.service.shop.operation.aop.Record)")
    public void returnRuning(JoinPoint point){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//获取request
    }
}
