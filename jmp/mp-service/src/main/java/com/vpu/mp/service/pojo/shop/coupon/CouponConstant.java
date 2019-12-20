package com.vpu.mp.service.pojo.shop.coupon;

/**
 *  优惠卷常量
 * @author 孔德成
 * @date 2019/11/25 15:34
 */
public class CouponConstant {


    //******优惠卷类型 0普通 1分裂
    /**
     * 普通优惠卷
     */
    public static final byte COUPON_TYPE_NORMAL =0;
    /**
     * 分裂优惠卷
     */
    public static final byte COUPON_TYPE_SPILT =1;

    //*********发卷来源   1表单送券2支付送券3活动送券4积分兑换5直接领取6分裂优惠券7crm领券8幸运大抽奖9定向发券
    /**
     * 支付送券
     */
    public static final byte COUPON_GIVE_SOURCE_PAY_AWARD=2;



}
