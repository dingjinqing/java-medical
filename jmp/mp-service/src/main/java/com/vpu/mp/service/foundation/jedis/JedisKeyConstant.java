package com.vpu.mp.service.foundation.jedis;

/**
 * redis key
 * @author 卢光耀
 * @date 2019-09-06 09:59
 *
*/
public class JedisKeyConstant {
    public static final String SEND_USER_KEY = "send:user:";

    /**taskJob lock*/
    public static final String TASK_JOB_LOCK = "lock:task:";

    /**商品品牌*/
    public static final String GOODS_BRAND = "goods:brand:shop-id_";
    /**商家分类*/
    public static final String GOODS_SORT= "goods:sort:shop-id_";
    /**Es init*/
    public static final String ES_INIT= "es:init";

    /**
     * goods lock(库存、销量)
     */
    public static final String GOODS_LOCK = "lock:goods:";



    /**
     * 拼团处理订单定时任务
     */
    public static final String TASK_JOB_LOCK_ORDER_GROUP_BUY = "lock:task:order:group_buy:";

    public static class NotResubmit{
        /**
         * 下单锁（同一个用户同时只会存在一个正常可以完成的下单请求）+shopId+userid
         */
        public static final String ORDER_SUBMIT = "lock:orderSubmit:";
    }
}
