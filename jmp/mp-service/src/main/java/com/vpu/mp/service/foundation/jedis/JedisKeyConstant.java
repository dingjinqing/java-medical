package com.vpu.mp.service.foundation.jedis;

/**
 * redis key
 * @author 卢光耀
 * @date 2019-09-06 09:59
 *
*/
public class JedisKeyConstant {
    public static final String SEND_USER_KEY = "send:user:";

    /**
     * taskJob lock
     */
    public static final String TASK_JOB_LOCK = "lock:task:";

    /**
     * goods lock(库存、销量)
     */
    public static final String GOODS_LOCK = "lock:goods:";
}
