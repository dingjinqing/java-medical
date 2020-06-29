package com.vpu.jmd.jedis;

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


    /**
     * Es init
     */
    public static final String ES_INIT = "es:init";

    /**
     * goods lock(库存、销量)
     */
    public static final String GOODS_LOCK = "lock:goods:";

    //店铺配置的缓存
    /**
     * 分词配置
     */
    public static final String CONFIG_ANALYZER_STATUS = "config:analyzer:";
    /**
     * 分享配置
     */
    public static final String CONFIG_SHARE_CONFIG = "config:shareConfig:";
    /**
     * 是否显示logo
     */
    public static final String CONFIG_SHOW_LOGO = "config:showLogo:";
    /**
     * logo点击的跳转链接
     */
    public static final String CONFIG_LOGO_LINK = "config:logoLink:";
    /**
     * 店铺风格
     */
    public static final String CONFIG_SHOP_STYLE = "config:shopStyle:";
    /**
     * 店铺底部导航配置
     */
    public static final String CONFIG_BOTTOM = "config:bottom:";

    /**
     * 装修缓存
     */
    public static final String PAGE_DECORATE = "page:decorate:shop_id_%s:page_id_%s";
    /**
     * 店铺悬浮窗配置缓存
     */
    public static final String CONFIG_SUSPEND_WINDOW = "config:suspend:window:";


    /**
     * 拼团处理订单定时任务
     */
    public static final String TASK_JOB_LOCK_ORDER_GROUP_BUY = "lock:task:order:group_buy:";

    public static class NotResubmit {
        /**
         * 下单锁（同一个用户同时只会存在一个正常可以完成的下单请求）+shopId+userid
         */
        public static final String ORDER_SUBMIT = "lock:orderSubmit";
    }
}
