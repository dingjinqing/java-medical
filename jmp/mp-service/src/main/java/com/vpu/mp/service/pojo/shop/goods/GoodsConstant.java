package com.vpu.mp.service.pojo.shop.goods;

/**
 * @author 李晓冰
 * @date 2019年10月14日
 * 商品及相关类的通用常量对应类
 */
public class GoodsConstant {
    /****** 商品活动类型对应码 *****/
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
    /**************** 结束 *****************/

    /****** 商品列表各种活动的处理优先级 *****/
    /**秒杀*/
    public final static Byte ACTIVITY_SEC_KILL_PRIORITY = 1;
    /**预售*/
    public final static Byte ACTIVITY_PRE_SALE_PRIORITY = 5;
    /**砍价*/
    public final static Byte ACTIVITY_BARGAIN_PRIORITY = 10;
    /**拼团*/
    public final static Byte ACTIVITY_GROUP_BUY_PRIORITY = 15;
    /**会员专享处理*/
    public final static Byte ACTIVITY_CARD_EXCLUSIVE_PRIORITY = 20;
    /**首单特惠*/
    public final static Byte ACTIVITY_FIRST_SPECIAL_PRIORITY = 25;
    /**限时降价*/
    public final static Byte ACTIVITY_REDUCE_PRICE_PRIORITY = 30;
    /**等级卡会员价*/
    public final static Byte ACTIVITY_MEMBER_GRADE_PRIORITY = 35;
    /**优惠券*/
    public final static Byte ACTIVITY_COUPON_PRIORITY = 40;
    /** 满折满减 */
    public final static Byte ACTIVITY_FULL_REDUCTION_PRIORITY = 45;

    /**************** 结束 *****************/

    /**************** 营销活动启用和停止状态 *****************/
    public final static Byte USE_STATUS = 1;
    public final static Byte STOP_STATUS = 0;
    /**************** 结束 *****************/

    /*** 销售状态：在售*/
    public static final Byte ON_SALE = 1;
    public static final Byte OFF_SALE = 0;

    /*** 会员专享状态：是*/
    public static final Byte CARD_EXCLUSIVE = 1;
    public static final Byte NOT_CARD_EXCLUSIVE = 0;

    /*** 可在商品列表,详情展示时标签该字段的值*/
    public static final Byte SHOW_LABEL = 1;
    public static final Integer LABEL_GTA_DEFAULT_VALUE = 0;
    /***商品评价状态对应码*/
    public static final Byte NOT_AUDIT = 0;
    public static final Byte PASS_AUDIT = 1;
    public static final Byte NOT_PASS_AUDIT = 2;


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

    public static boolean isGoodsTypeIn13510(Byte goodsType) {
        return GoodsConstant.ACTIVITY_TYPE_GROUP_BUY.equals(goodsType)|| GoodsConstant.ACTIVITY_TYPE_BARGAIN.equals(goodsType)
            || GoodsConstant.ACTIVITY_TYPE_SEC_KILL.equals(goodsType) || GoodsConstant.ACTIVITY_TYPE_PRE_SALE .equals(goodsType);
    }

    /**
     *  是否是需要告知前端活动id的活动,目前有 1,3,5,6,10，18
     * @param goodsType 活动类型
     */
    public static boolean isNeedReturnActivity(Byte goodsType){
        return GoodsConstant.ACTIVITY_TYPE_GROUP_BUY.equals(goodsType)|| GoodsConstant.ACTIVITY_TYPE_BARGAIN.equals(goodsType)
            || GoodsConstant.ACTIVITY_TYPE_SEC_KILL.equals(goodsType) || GoodsConstant.ACTIVITY_TYPE_PRE_SALE .equals(goodsType)
            || GoodsConstant.ACTIVITY_TYPE_REDUCE_PRICE.equals(goodsType) || GoodsConstant.ACTIVITY_TYPE_FIRST_SPECIAL.equals(goodsType);

    }
}
