package com.vpu.mp.service.foundation.util.lock;

import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Slf4j
@Component
public class RedisLockAspect extends ShopBaseService {

    @Autowired
    private JedisManager jedisManager;

    private String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    @Around("@annotation(com.vpu.mp.service.foundation.util.lock.annotation.RedisLock)")
    public void around(ProceedingJoinPoint joinPoint) throws MpException {
        log.info("redis批量锁start");
        //开始时间
        long nano = System.nanoTime();
        //
        Signature signature = joinPoint.getSignature();
        Object[] args = joinPoint.getArgs();
        //获取参数名
        MethodSignature methodSignature = (MethodSignature) signature;
        //获取注解
        RedisLock redisLockAnnotation = methodSignature.getMethod().getAnnotation(RedisLock.class);
        //keys
        List<String> keys = null;
        //解锁时value
        String value = Util.randomId();
        //获取失败时需要释放锁的list
        List<String> fail = null;
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
            keys.set(i, new StringBuilder().append(redisLockAnnotation.prefix()).append(getShopId()).append(keys.get(i)).toString());
        }
        try {
            do {
                //加锁
                fail = addLocks(keys, value, redisLockAnnotation.expiredTime());
                if (fail.size() == keys.size()) {
                    //获取批量锁失败
                    log.error("批量锁获取失败,当前获取到:", fail.toString());
                    releaseLocks(fail, value);
                    Thread.sleep(200);
                } else {
                    //成功调用代理方法
                    joinPoint.proceed();
                }
            } while (nano - System.nanoTime() < redisLockAnnotation.maxWait() * 1000000);
        } catch (Throwable throwable) {
            log.error("批量锁异常:{}", throwable.getStackTrace().toString());
        }finally {
            //释放
            releaseLocks(keys, value);
            if(fail.size() != keys.size()){
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_GET_LOCK_FAIL);
            }
        }
    }

    /**
     * 添加锁
     * @param keys  锁list
     * @param value value
     * @return 加锁成功的key
     */
    public List<String> addLocks(List<String> keys, String value, int expiredTime) {
        List<String> fail = new ArrayList<>();
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
        return fail;
    }

    /**
     * 释放锁
     * @param keys  锁list
     * @param value value
     */
    public void releaseLocks(List<String> keys, String value) {
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
