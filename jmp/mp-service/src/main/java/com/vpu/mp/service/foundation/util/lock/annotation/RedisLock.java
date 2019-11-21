package com.vpu.mp.service.foundation.util.lock.annotation;

import javax.validation.constraints.NotBlank;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redis批量锁
 * @author 王帅
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisLock {

    /**
     * 轮询锁等待时间（毫秒）
     */
    long maxWait() default 3 *1000;

    /**
     * 轮询锁最大次数
     */
    int pollingLimit() default 30;

    /**
     * 锁过期时间（毫秒）
     */
    int expiredTime() default 2 * 60 * 1000;

    /**
     * key前缀
     */
    String prefix();
}
