package com.vpu.mp.service.foundation.data;

/**
 * 基础静态常量存放处
 *
 * @date 2019/10/24 16:42
 */
public final  class  BaseConstant {

    //******************** 营销活动活动*******************/
    /**
     * 活动启用
     */
    public static final Byte ACTIVITY_STATUS_NORMAL = 1;
    /**
     * 活动禁用
     */
    public static final Byte ACTIVITY_STATUS_DISABLE = 0;

    /** 普通商品 */
    public final static Byte ACTIVITY_TYPE_GENERAL = 0;
    /** 拼团商品 */
    public final static Byte ACTIVITY_TYPE_GROUP_BUY = 1;
    /** 返利商品 */
    public final static Byte ACTIVITY_TYPE_REBATE = 2;
    /** 砍价商品 */
    public final static Byte ACTIVITY_TYPE_BARGAIN = 3;

    /** 积分兑换商品 */
    public final static Byte ACTIVITY_TYPE_INTEGRAL = 4;
    /** 秒杀商品 */
    public final static Byte ACTIVITY_TYPE_SEC_KILL = 5;
    /** 限时降价 返利 */
    public final static Byte ACTIVITY_TYPE_REDUCE_PRICE = 6;
    /** 加价购 返利 */
    public final static Byte ACTIVITY_TYPE_PURCHASE_PRICE = 7;
    /** 拼团抽奖 */
    public final static Byte ACTIVITY_TYPE_GROUP_DRAW = 8;
    /** 一口价 */
    public final static Byte ACTIVITY_TYPE_PACKAGE_SALE = 9;
    /** 定金膨胀 */
    public final static Byte ACTIVITY_TYPE_PRE_SALE = 10;
    /** 赠品 */
    public final static Byte ACTIVITY_TYPE_GIFT = 11;
    /** 幸运大抽奖 */
    public final static Byte ACTIVITY_TYPE_LOTTERY_PRESENT = 12;
    /** 限次卡兑换 */
    public final static Byte ACTIVITY_TYPE_EXCHANG_ORDER = 13;
    /** 好友助力 */
    public final static Byte ACTIVITY_TYPE_PROMOTE_ORDER = 14;
    /** 满包邮 */
    public final static Byte ACTIVITY_TYPE_FREESHIP_ORDER = 15;
    /** 测评 */
    public final static Byte ACTIVITY_TYPE_ASSESS_ORDER = 16;
    /** 送礼 */
    public final static Byte ACTIVITY_TYPE_GIVE_GIFT = 17;
    /** 首单特惠 */
    public final static Byte ACTIVITY_TYPE_FIRST_SPECIAL = 18;
    /** 优惠券活动 */
    public final static Byte ACTIVITY_TYPE_COUPON = 19;
    /** 满折满减*/
    public final static Byte ACTIVITY_TYPE_FULL_REDUCTION = 20;
    /** 等级会员特殊照顾（不是营销活动）*/
    public final static Byte ACTIVITY_TYPE_MEMBER_GRADE = 21;
    /** 会员专享型（不是营销活动）*/
    public final static Byte ACTIVITY_TYPE_MEMBER_EXCLUSIVE = 22;
    /** 代付订单 */
    public final static Byte ACTIVITY_TYPE_PAY_FOR_ANOTHER = 99;
    /** 扫码购订单 */
    public final static Byte ACTIVITY_TYPE_SWEEP_CODE_BUY = 100;


    //*************************首单特惠 超出限购数量后，买家不可继续添加购买该商品 0可以 1不可以
    /**
     * 继续购买
     */
    public final static byte FIRST_SPECIAL_LIMIT_FLAG_CONTINUE =0;
    /**
     * 限制购买
     */
    public final static byte FIRST_SPECIAL_LIMIT_FLAG_CONFINE =1;



}
