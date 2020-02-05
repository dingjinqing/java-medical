package com.vpu.mp.service.pojo.shop.market.lottery;



/**
 * @author 孔德成
 * @date 2019/8/5 10:58
 */
public class LotteryConstant {
    /***********抽奖来源*****/
    public final static Byte LOTTERY_TIME_ALL=0;
    /**
     * 免费
     */
    public final static Byte LOTTERY_TIME_FREE=1;
    /**
     * 分享
     */
    public final static Byte LOTTERY_TIME_SHARE=2;
    /**
     * 积分
     */
    public final static Byte LOTTERY_TIME_SCORE=3;



    /**
     * 未中奖
     */
    public final static byte LOTTERY_TYPE_NULL=-2;
    /**
     * 奖品已经送完
     */
    public final static byte LOTTERY_TYPE_SEND_OUT=-1;
    /**
     * 鼓励奖
     */
    public final static byte LOTTERY_TYPE_HEARTEN=0;
    /**
     * 积分
     */
    public final static byte LOTTERY_TYPE_SCORE=1;
    /**
     * 账户
     */
    public final static byte LOTTERY_TYPE_BALANCE  =2;
    /**
     * 优惠卷
     */
    public final static byte LOTTERY_TYPE_COUPON  =3;
    /**
     * 商品
     */
    public final static byte LOTTERY_TYPE_GOODS  =4;
    /**
     * 自定义
     */
    public final static byte LOTTERY_TYPE_CUSTOM  =5;

}
