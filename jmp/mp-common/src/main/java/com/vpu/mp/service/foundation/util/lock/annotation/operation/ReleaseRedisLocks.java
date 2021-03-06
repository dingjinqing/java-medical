package com.vpu.mp.service.foundation.util.lock.annotation.operation;

import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 王帅
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReleaseRedisLocks {
}
