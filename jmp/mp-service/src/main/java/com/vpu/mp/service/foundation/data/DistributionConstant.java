package com.vpu.mp.service.foundation.data;

/**
 * 分销返利常量
 * @author 王帅
 */
public class DistributionConstant {
    /**
     * 分销等级1-5
     */
    public static Byte level_1 = 1;
    public static Byte level_2 = 2;
    public static Byte level_3 = 3;
    public static Byte level_4 = 4;
    public static Byte level_5 = 5;

    /**
     * 返利类型:1返利订单
     */
    public static Byte REBATE_ORDER = 1;

    /**
     * 返利等级
     * 0自购返利自己；1返利直接上级；2返利间件上级
     */
    public static Byte REBATE_LEVEL_0 = 0;
    public static Byte REBATE_LEVEL_1 = 1;
    public static Byte REBATE_LEVEL_2 = 2;

    /**
     * 返利商品类型 0：全部商品；1：部分商品
     */
    public static Byte ALL_GOODS = 0;
    public static Byte PART_GOODS = 1;

    /**
     * 返利类型 0：自购返利；1：直接返利；2：间接返利
     */
    public static Byte REBATE_TYPE_SELF = 0;
    public static Byte REBATE_TYPE_DIRECT = 1;
    public static Byte REBATE_TYPE_INDIRECT = 2;

    /**
     * 是否赠送优惠券 0：不赠送；1：赠送
     */
     public static Byte NOT_SEND_COUPON = 0;
     public static Byte SEND_COUPON = 1;

    /**
     * '佣金计算方式;0:商品实际支付金额*佣金比例；1：商品实际利润（实际支付金额-成本价）* 佣金比例'
     */
    public static Byte STRATEGY_TYPE_PAYMENT = 0;
    public static Byte STRATEGY_TYPE_PROFIT = 1;
}
