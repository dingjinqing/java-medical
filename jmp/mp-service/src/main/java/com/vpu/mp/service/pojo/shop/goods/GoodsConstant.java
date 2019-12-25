package com.vpu.mp.service.pojo.shop.goods;

import com.vpu.mp.service.foundation.data.BaseConstant;

/**
 * @author 李晓冰
 * @date 2019年10月14日
 * 商品及相关类的通用常量对应类
 */
public class GoodsConstant {
    /****** 商品列表各种活动的处理优先级 *****/
    /**
     * 秒杀
     */
    public final static Byte ACTIVITY_SEC_KILL_PRIORITY = 1;
    /**
     * 预售
     */
    public final static Byte ACTIVITY_PRE_SALE_PRIORITY = 5;
    /**
     * 砍价
     */
    public final static Byte ACTIVITY_BARGAIN_PRIORITY = 10;
    /**
     * 拼团
     */
    public final static Byte ACTIVITY_GROUP_BUY_PRIORITY = 15;
    /**
     * 会员专享处理
     */
    public final static Byte ACTIVITY_CARD_EXCLUSIVE_PRIORITY = 20;
    /**
     * 首单特惠
     */
    public final static Byte ACTIVITY_FIRST_SPECIAL_PRIORITY = 25;
    /**
     * 限时降价
     */
    public final static Byte ACTIVITY_REDUCE_PRICE_PRIORITY = 30;
    /**
     * 等级卡会员价
     */
    public final static Byte ACTIVITY_MEMBER_GRADE_PRIORITY = 35;
    /**
     * 优惠券
     */
    public final static Byte ACTIVITY_COUPON_PRIORITY = 40;
    /**
     * 满折满减
     */
    public final static Byte ACTIVITY_FULL_REDUCTION_PRIORITY = 45;

    /**************** 结束 *****************/

    /**************** 小程序-装修商品-手动推荐或自动推荐 *****************/
    public final static Byte AUTO_RECOMMEND = 0;
    public final static Byte POINT_RECOMMEND = 1;

    /**************** 结束 *****************/

    /*** 销售状态：在售*/
    public static final Byte ON_SALE = 1;
    /**
     * 下架
     */
    public static final Byte OFF_SALE = 0;

    /**
     * 砍价活动，指定人数砍价
     */
    public static final Byte BARGAIN_TYPE_FIXED = 0;

    /**
     * 商品默认品牌id值
     */
    public static final Integer GOODS_DEFAULT_BRAND_ID=0;
    /**
     * 商品标签可以展示的最大数量
     */
    public static final Integer GOODS_LABEL_MAX_COUNT=5;
    /**
     * 商品违规下架
     */
    public static final Byte INVALIDATE_OFF_SALE = 2;

    /**
     * 指定时间上架
     */
    public static final Byte POINT_TIME_TO_ON_SALE = 1;

    /*** 会员专享状态：是*/
    public static final Byte CARD_EXCLUSIVE = 1;
    public static final Byte NOT_CARD_EXCLUSIVE = 0;

    /*** 可在商品列表,详情展示时标签该字段的值*/
    public static final Byte SHOW_LABEL = 1;
    public static final Integer LABEL_GTA_DEFAULT_VALUE = 0;


    /*** 是推荐品牌,普通品牌，推荐分类，普通分类码*/
    public static final Byte RECOMMEND_BRAND = 1;
    public static final Byte NORMAL_BRAND = 0;
    public static final Byte RECOMMEND_SORT = 1;
    public static final Byte NORMAL_SORT = 0;
    public static final Byte HAS_CHILD = 1;
    public static final Byte HAS_NO_CHILD = 0;
    public static final Short ROOT_LEVEL = 0;
    public static final Short SECOND_LEVEL = 1;
    public static final Short THIRD_LEVEL = 2;
    /*** 未关联品牌分类时的分类id*/
    public static final Integer NO_CLASSIFY_ID = 0;

    /*** 一级分类的父节点id值：0*/
    public static final Integer ROOT_PARENT_ID = 0;

    /***展示售罄商品*/
    public static final Byte SOLD_OUT_GOODS_SHOW = 1;

    /**
     * 1默认规格，0自定义规格（多规格）
     */
    public static final Byte IS_DEFAULT_PRODUCT_Y = 1;
    public static final Byte IS_DEFAULT_PRODUCT_N = 0;

    /**
     * 小程序-商品分类页面-目录项类型码
     * 1全部品牌，2推荐品牌，3推荐品牌列表展示，4推荐品牌按分类展示，
     * 5推荐分类，6普通分类，7商品内容
     */
    public static final Byte ALL_BRAND_TYPE = 1;
    public static final Byte RECOMMEND_BRAND_TYPE = 2;
    public static final Byte RECOMMEND_BRAND_LIST_TYPE = 3;
    public static final Byte RECOMMEND_BRAND_CLASSIFY_TYPE = 4;
    public static final Byte RECOMMEND_SORT_TYPE = 5;
    public static final Byte NORMAL_SORT_TYPE = 6;
    public static final Byte GOODS_TYPE = 7;

    public static boolean isGoodsTypeIn13510(Byte goodsType) {
        return BaseConstant.ACTIVITY_TYPE_GROUP_BUY.equals(goodsType) || BaseConstant.ACTIVITY_TYPE_BARGAIN.equals(goodsType)
            || BaseConstant.ACTIVITY_TYPE_SEC_KILL.equals(goodsType) || BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(goodsType);
    }

    /**
     * 是否是需要告知前端活动id的活动,目前有 1,3,5,6,10,18
     *
     * @param goodsType 活动类型
     */
    public static boolean isNeedReturnActivity(Byte goodsType) {
        return BaseConstant.ACTIVITY_TYPE_GROUP_BUY.equals(goodsType) || BaseConstant.ACTIVITY_TYPE_BARGAIN.equals(goodsType)
            || BaseConstant.ACTIVITY_TYPE_SEC_KILL.equals(goodsType) || BaseConstant.ACTIVITY_TYPE_PRE_SALE.equals(goodsType)
            || BaseConstant.ACTIVITY_TYPE_REDUCE_PRICE.equals(goodsType) || BaseConstant.ACTIVITY_TYPE_FIRST_SPECIAL.equals(goodsType);

    }
}
