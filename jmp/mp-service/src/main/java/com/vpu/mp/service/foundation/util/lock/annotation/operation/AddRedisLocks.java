package com.vpu.mp.service.foundation.util.lock.annotation.operation;

import com.vpu.mp.service.foundation.util.lock.annotation.RedisLock;

/**
 * @author 王帅
 */
public @interface AddRedisLocks {
    RedisLock redisLock();
}
