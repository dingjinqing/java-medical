package com.vpu.mp.service.pojo.shop.goods;

/**
 * @author 李晓冰
 * @date 2019年10月14日
 * 商品及相关类的通用常量对应类
 */
public class GoodsConstant {
    /** 商品活动类型对应码 */
    /** 普通商品 */
    public final static byte GOODS_TYPE_GENERAL = 0;
    /** 拼团商品 */
    public final static byte GOODS_TYPE_PIN_GROUP = 1;
    /** 返利商品 */
    public final static byte GOODS_TYPE_REBATE = 2;
    /** 砍价商品 */
    public final static byte GOODS_TYPE_BARGAIN = 3;

    /** 积分兑换商品 */
    public final static byte GOODS_TYPE_INTEGRAL = 4;
    /** 秒杀商品 */
    public final static byte GOODS_TYPE_SECKILL = 5;
    /** 限时降价 返利 */
    public final static byte GOODS_TYPE_REDUCE_PRICE = 6;
    /** 加价购 返利 */
    public final static byte GOODS_TYPE_PURCHASE_PRICE = 7;
    /** 拼团抽奖 */
    public final static byte GOODS_TYPE_GROUP_DRAW = 8;
    /** 一口价 */
    public final static byte GOODS_TYPE_PACKAGE_SALE = 9;
    /** 定金膨胀 */
    public final static byte GOODS_TYPE_PRE_SALE = 10;
    /** 赠品 */
    public final static byte GOODS_TYPE_GIFT = 11;
    /** 幸运大抽奖 */
    public final static byte GOODS_TYPE_LOTTERY_PRESENT = 12;
    /** 限次卡兑换 */
    public final static byte GOODS_TYPE_EXCHANG_ORDER = 13;
    /** 好友助力 */
    public final static byte GOODS_TYPE_PROMOTE_ORDER = 14;
    /** 满包邮 */
    public final static byte GOODS_TYPE_FREESHIP_ORDER = 15;
    /** 测评 */
    public final static byte GOODS_TYPE_ASSESS_ORDER = 16;
    /** 送礼 */
    public final static byte GOODS_TYPE_GIVE_GIFT = 17;
    /** 首单特惠 */
    public final static byte GOODS_TYPE_FIRST_SPECIAL = 18;
    /** 代付订单 */
    public final static byte GOODS_TYPE_PAY_FOR_ANOTHER = 99;
    /** 扫码购订单 */
    public final static byte GOODS_TYPE_SWEEP_CODE_BUY = 100;

    /** 商品存在优惠券信息*/
    public final static byte GOODS_TYPE_HAS_COUPON = 120;
    /** 商品存在满减活动 */
    public final static byte GOODS_TYPE_HAS_FULL_REDUCTION = 121;


    /*** 销售状态：在售*/
    public static final Byte ON_SALE = 1;

    /*** 会员专享状态：是*/
    public static final Byte CARD_EXCLUSIVE = 1;

    /*** 可在商品列表展示的标签*/
    public static final Byte GOODS_LIST = 1;

    /*** 是推荐品牌,普通品牌，推荐分类，普通分类码*/
    public static final Byte RECOMMEND_BRAND = 1;
    public static final Byte NORMAL_BRAND = 0;
    public static final Byte RECOMMEND_SORT = 1;
    public static final Byte NORMAL_SORT = 0;
    /*** 未关联品牌分类时的分类id*/
    public static final Integer NO_CLASSIFY_ID = 0;

    /*** 一级分类的父节点id值：0*/
    public static final Integer ROOT_PARENT_ID = 0;

    /***展示售罄商品*/
    public static final Byte SOLD_OUT_GOODS_SHOW = 1;

    /**
     * 小程序-商品分类页面-目录项类型码
     * 1全部品牌，2推荐品牌，3推荐品牌列表展示，4推荐品牌按分类展示，
     * 5推荐分类，6普通分类，7商品内容
     */

    public static final Byte ALL_BRAND_TYPE  = 1;
    public static final Byte RECOMMEND_BRAND_TYPE  = 2;
    public static final Byte RECOMMEND_BRAND_LIST_TYPE=3;
    public static final Byte RECOMMEND_BRAND_CLASSIFY_TYPE=4;
    public static final Byte RECOMMEND_SORT_TYPE  = 5;
    public static final Byte NORMAL_SORT_TYPE  = 6;
    public static final Byte GOODS_TYPE  = 7;
}
