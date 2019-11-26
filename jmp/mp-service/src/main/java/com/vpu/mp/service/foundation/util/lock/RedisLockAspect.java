package com.vpu.mp.service.foundation.util.lock;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockField;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
 import java.util.List;
import java.util.stream.Collectors;

/**
 * 批量锁
 * @author 王帅
 */
@Aspect
@Slf4j
@Component
public class RedisLockAspect extends ShopBaseService {

    @Autowired
    private JedisManager jedisManager;

    private String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Around("@annotation(com.vpu.mp.service.foundation.util.lock.annotation.RedisLock)")
    public void around(ProceedingJoinPoint joinPoint) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, MpException {
        log.info("redis批量锁start");
        //keys
        List<String> keys = null;
        //开始时间
        long nano = System.nanoTime();
        //
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        //获取方法签名
        MethodSignature methodSignature = (MethodSignature) signature;
        //形参
        Parameter[] parameters = methodSignature.getMethod().getParameters();
        //key所在形参索引
        Integer keyIndex = null;
        //获取key所在形参索引
        for (int i = 0, length = parameters.length; i < length; i++) {
            if(null !=parameters[i].getDeclaredAnnotation(RedisLockKeys.class)){
                keyIndex = i;
                break;
            }
        }
        //keys实参
        if(args[keyIndex.intValue()] instanceof List){
            //集合类
            List<Object> keyObjs = (List<Object>) args[keyIndex.intValue()];
            //获取fields
            Field[] keyObjFields = keyObjs.get(0).getClass().getDeclaredFields();
            //key field name
            String keyFieldName = null;
            //
            for (int i = 0, length = keyObjFields.length; i < length; i++) {
                if(keyObjFields[i].isAnnotationPresent(RedisLockField.class)){
                    keyFieldName = keyObjFields[i].getName();
                    break;
                }
            }
            //初始化keys
            keys = new ArrayList<>(keyObjs.size());
            for (Object keyObj : keyObjs) {
                keys.add(PropertyUtils.getProperty(keyObj, keyFieldName).toString());
            }
        }else {
            //非集合类
            //获取fields
            Field[] keyObjFields = args[keyIndex.intValue()].getClass().getDeclaredFields();
            String keyFieldName = null;
            //
            for (int i = 0, length = keyObjFields.length; i < length; i++) {
                if(keyObjFields[i].isAnnotationPresent(RedisLockField.class)){
                    keyFieldName = keyObjFields[i].getName();
                    break;
                }
            }
            //初始化keys
            keys = new ArrayList<>(1);
            keys.add(PropertyUtils.getProperty(args[keyIndex.intValue()], keyFieldName).toString());
        }
        //获取注解
        RedisLock redisLockAnnotation = methodSignature.getMethod().getAnnotation(RedisLock.class);
        //解锁时value
        String value = Util.randomId();
        //获取锁参数
        for (int i = 0, length = args.length; i < length; i++) {
            if (args[i].getClass().isAnnotationPresent(RedisLockKeys.class)) {
                keys = (List<String>) args[i];
                break;
            }
        }
        //去重
        keys = keys.stream().distinct().collect(Collectors.toList());
        //初始化key
        for (int i = 0, length = keys.size(); i < length; i++) {
            keys.set(i, new StringBuilder().append(redisLockAnnotation.prefix()).append(getShopId()).append(":").append(keys.get(i)).toString());
        }
        //获取失败时需要释放锁的list
        List<String> fail = new ArrayList<>(keys.size());
        try {
            do {
                fail.clear();
                //加锁
                addLocks(keys, value, redisLockAnnotation.expiredTime(), fail);
                if (fail.size() < keys.size()) {
                    //获取批量锁失败
                    log.error("批量锁获取失败,当前获取到:", fail.toString());
                    releaseLocks(fail, value);
                    Thread.sleep(200);
                } else {
                    log.info("批量锁获取成功，准备调用代理方法");
                    //成功调用代理方法
                    joinPoint.proceed();
                    log.info("调用代理方法成功");
                    break;
                }
            } while (nano - System.nanoTime() < redisLockAnnotation.maxWait() * 1000000);
            if(fail.size() != keys.size()){
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_GET_LOCK_FAIL);
            }
        } catch (Throwable throwable) {
            log.error("批量锁执行joinPoint.proceed()异常");
            throw new MpException(JsonResultCode.CODE_ORDER_UPDATE_STOCK_FAIL);
        }finally {
            log.info("释放redis锁");
            //释放
            releaseLocks(keys, value);
        }
    }

    /**
     * 添加锁
     * @param keys  锁list
     * @param value value
     * @return 加锁成功的key
     */
    public void addLocks(List<String> keys, String value, int expiredTime, List<String> fail) {
        try (Jedis jedis = jedisManager.getJedisPool().getResource()) {
            Pipeline pipeline = jedis.pipelined();
            for (String key : keys) {
                pipeline.set(key, value, JedisManager.SET_IF_NOT_EXIST, JedisManager.SET_WITH_EXPIRE_TIME, expiredTime);
            }
            List<Object> results = pipeline.syncAndReturnAll();
            for (int i = 0, length = results.size(); i < length; i++) {
                String result = (String) results.get(i);
                if ("OK".equals(result)) {
                    //setnx成功，获得锁
                    fail.add(keys.get(i));
                }
            }
        }
    }

    /**
     * 释放锁
     * @param keys  锁list
     * @param value value
     */
    public void releaseLocks(List<String> keys, String value) {
        if(CollectionUtils.isEmpty(keys)) {
            return;
        }
        try (Jedis jedis = jedisManager.getJedisPool().getResource()) {
            Pipeline pipeline = jedis.pipelined();
            //删除锁
            for (int i = 0, length = keys.size(); i < length; i++) {
                pipeline.eval(script, Collections.singletonList(keys.get(i)), Collections.singletonList(value));
            }
            pipeline.sync();
        }
    }
}
