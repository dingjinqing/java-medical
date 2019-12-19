package com.vpu.mp.service.pojo.shop.market.payaward;

/**
 * 已支付有礼常量
 *
 * @author 孔德成
 * @date 2019/12/5 15:40
 */
public class PayAwardConstant {

    //*********************领奖状态 0未领取1已领取

    /**
     * 已领取
     */
    public final static byte PAY_AWARD_GIVE_STATUS_RECEIVED = 1;



    //***********************奖品类型
/**
 * 0	无状态
 * 1	普通优惠卷
 * 2	分裂优惠卷
 * 3	幸运大抽奖
 * 4	余额
 * 5	奖品
 * 6	积分
 * 7	自定义
 */
    /**
     * 无奖励
     */
    public final static byte GIVE_TYPE_NO_PRIZE = 0;
    /**
     *普通优惠卷
     */
    public final static byte GIVE_TYPE_ORDINARY_COUPON = 1;
    /**
     * 分裂优惠卷
     */
    public final static byte GIVE_TYPE_SPLIT_COUPON = 2;
    /**
     * 奖品
     */
    public final static byte GIVE_TYPE_SPLIT_GOODS = 5;
}
