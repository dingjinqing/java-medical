package com.vpu.mp.service.foundation.util.lock;

import com.google.common.collect.Lists;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockField;
import com.vpu.mp.service.foundation.util.lock.annotation.RedisLockKeys;
import com.vpu.mp.service.foundation.util.lock.annotation.operation.AddRedisLocks;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 批量锁
 * @author 王帅
 */
@Aspect
@Component
public final class RedisLockAspect extends ShopBaseService {

    @Autowired
    private JedisManager jedisManager;

    private String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    private ThreadLocal<ArrayDeque<LockEntity>> lockEntities = ThreadLocal.withInitial(ArrayDeque<LockEntity>::new);

    @Autowired protected HttpServletRequest request;

    @Around("@annotation(com.vpu.mp.service.foundation.util.lock.annotation.RedisLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws MpException {
        logger().info("redis环绕批量锁start");
        addLocks(joinPoint);
        logger().info("redis环绕批量锁调用代理方法start");
        Object proceed;
        try {
            proceed = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger().error("批量锁执行joinPoint.proceed()异常", throwable);
            throw new MpException(JsonResultCode.CODE_ORDER_UPDATE_STOCK_FAIL);
        }
        releaseLocks(joinPoint);
        logger().info("redis环绕批量锁调用代理方法end");
        return proceed;
    }

    /**
     * 获取方法实参keys
     * @param args 实参数组
     * @param methodSignature 方法签名
     * @return keys
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private List<String> getKeys(Object[] args, MethodSignature methodSignature) throws MpException {
        List<String> keys = Lists.newArrayList();
        //防重复提交策略
        List<String> noResubmitKey = getNoResubmitKey(methodSignature);
        if(CollectionUtils.isNotEmpty(noResubmitKey)) {
            return noResubmitKey;
        }
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
        if(keyIndex == null){
            return keys;
        }
        //keys实参
        if(args[keyIndex] instanceof List){
            //集合类
            List<Object> keyObjs = (List<Object>) args[keyIndex];
            if(keyObjs.get(0).getClass().getClassLoader() == null){
                //非开发设计的类
                for (Object keyObj : keyObjs) {
                    keys.add(keyObj.toString());
                }
            }else {
                //获取fields
                Class className = keyObjs.get(0).getClass();
                //key field name
                String keyFieldName = null;
                //
                for (; className != Object.class; className = className.getSuperclass()) {
                    Field[] keyObjFields = className.getDeclaredFields();
                    for (int i = 0, length = keyObjFields.length; i < length; i++) {
                        if(keyObjFields[i].isAnnotationPresent(RedisLockField.class)){
                            keyFieldName = keyObjFields[i].getName();
                            break;
                        }
                    }
                    if(keyFieldName != null) {
                        break;
                    }
                }
                //初始化keys
                keys = new ArrayList<>(keyObjs.size());
                try {
                    for (Object keyObj : keyObjs) {

                        keys.add(PropertyUtils.getProperty(keyObj, keyFieldName).toString());
                    }
                }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    logger().error("redis获取锁PropertyUtils.getProperty错误");
                    throw new MpException(JsonResultCode.CODE_ORDER_GOODS_GET_LOCK_FAIL, "redis获取锁PropertyUtils.getProperty错误");
                }
            }

        } else if(args[keyIndex.intValue()].getClass().getClassLoader() == null) {
            //非集合类型但是为jdk自带类型
            keys = new ArrayList<>(1);
            keys.add(args[keyIndex.intValue()].toString());
        } else {
            //非集合类但是为自定义类型
            Class className = args[keyIndex.intValue()].getClass();
            //key field name
            String keyFieldName = null;
            //
            for (; className != Object.class; className = className.getSuperclass()) {
                Field[] keyObjFields = className.getDeclaredFields();
                for (int i = 0, length = keyObjFields.length; i < length; i++) {
                    if(keyObjFields[i].isAnnotationPresent(RedisLockField.class)){
                        keyFieldName = keyObjFields[i].getName();
                        break;
                    }
                }
                if(keyFieldName != null) {
                    break;
                }
            }
            //初始化keys
            keys = new ArrayList<>(1);
            try {
                keys.add(PropertyUtils.getProperty(args[keyIndex.intValue()], keyFieldName).toString());
            }catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                logger().error("redis获取锁PropertyUtils.getProperty错误");
                throw new MpException(JsonResultCode.CODE_ORDER_GOODS_GET_LOCK_FAIL, "redis获取锁PropertyUtils.getProperty错误");
            }
        }
        return keys;
    }

    private List<String> getNoResubmitKey(MethodSignature signature) {
        RedisLock redisLock = ((MethodSignature)signature).getMethod().getAnnotation(RedisLock.class);
        if(redisLock == null || !redisLock.noResubmit()) {
            return null;
        }
        logger().info("接口防重复提交");
        return Lists.newArrayList(request.getHeader("V-Token"));
    }

    /**
     * 添加锁
     * @param keys  锁list
     * @param value value
     */
    private void addLocks(List<String> keys, String value, int expiredTime, List<String> fail) {
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

    @Before("@annotation(com.vpu.mp.service.foundation.util.lock.annotation.operation.AddRedisLocks)")
    public void addLocks(JoinPoint joinPoint) throws MpException {
        logger().info("AddRedisLocks,加redis锁start");
        //锁对象
        LockEntity lockEntity = new LockEntity();
        //keys
        List<String> keys;
        //开始时间
        long nano = System.nanoTime();
        //获取签名
        Signature signature = joinPoint.getSignature();
        //获取keys
        keys = getKeys(joinPoint.getArgs(), (MethodSignature) signature);
        //keys为空，则结束
        if (CollectionUtils.isEmpty(keys)) {
            logger().info("AddRedisLocks,加redis锁(keys is null)end");
            return;
        }
        lockEntity.setValue(Util.randomId());
        //获取注解
        RedisLock redisLockAnnotation = ((MethodSignature) signature).getMethod().getAnnotation(RedisLock.class);
        if (redisLockAnnotation == null) {
            AddRedisLocks addRedisLocks = ((MethodSignature) signature).getMethod().getAnnotation(AddRedisLocks.class);
            redisLockAnnotation = addRedisLocks.redisLock();
        }
        //去重
        keys = keys.stream().distinct().collect(Collectors.toList());
        //初始化key
        for (int i = 0, length = keys.size(); i < length; i++) {
            keys.set(i, new StringBuilder().append(redisLockAnnotation.prefix()).append(redisLockAnnotation.noResubmit() ? StringUtils.EMPTY : getShopId()).append(":").append(keys.get(i)).toString());
        }
        //线程记录该次加锁keys
        lockEntity.setKeys(keys);
        lockEntities.get().push(lockEntity);
        //获取失败时需要释放锁的list
        List<String> fail = new ArrayList<>(keys.size());
        do {
            fail.clear();
            //加锁
            addLocks(keys, lockEntity.getValue(), redisLockAnnotation.expiredTime(), fail);
            if (fail.size() < keys.size()) {
                if (redisLockAnnotation.noResubmit()) {
                    //获取批量锁失败
                    logger().error("api接口防重复提交重构,key={}", keys.toString());
                    releaseLocks(fail, lockEntity.getValue());
                    throw new MpException(JsonResultCode.CODE_API_NO_RESUBMIT, "redis获取锁PropertyUtils.getProperty错误");

                }
                //获取批量锁失败
                logger().error("批量锁获取失败,当前获取到:{}", fail.toString());
                releaseLocks(fail, lockEntity.getValue());
            } else {
                logger().info("批量锁获取成功，执行后续方法");
                break;
            }
        } while (System.nanoTime() - nano < redisLockAnnotation.maxWait() * 1000000);
        if (fail.size() != keys.size()) {
            //释放
            releaseLocks(fail, lockEntity.getValue());
            throw new MpException(JsonResultCode.CODE_ORDER_GOODS_GET_LOCK_FAIL);
        }
        logger().info("AddRedisLocks,加redis锁end");
    }

    @Before("@annotation(com.vpu.mp.service.foundation.util.lock.annotation.operation.ReleaseRedisLocks)")
    public void releaseLocks(JoinPoint joinPoint){
        logger().info("releaseLocks,释放redis锁start");
        releaseLocks(lockEntities.get().getFirst().getKeys(), lockEntities.get().getFirst().getValue());
        logger().info("releaseLocks,释放redis锁end");
    }

    /**
     * 释放锁
     * @param keys  锁list
     * @param value value
     */
    private void releaseLocks(List<String> keys, String value) {
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
        //释放锁后清除key
        lockEntities.get().pop();
    }
}
