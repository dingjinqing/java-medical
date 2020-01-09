package com.vpu.mp.service.pojo.wxapp.market.prize;

/**
 * @author 孔德成
 * @date 2020/1/3 13:56
 */
public class PrizeConstant {
    //****************奖品来源

    /**
     * 奖品来源
     */
    public final static byte PRIZE_SOURCE_LOTTERY=0;
    /**
     * 奖品来源
     */
    public final static byte PRIZE_SOURCE_PAY_AWARD=3;
    /**
     * 奖品类型 todo
     */
    public final static byte PRIZE_SOURCE=1;


    //**************状态

    /**
     * 待领取
     */
    public final static Byte PRIZE_STATUS_UNCLAIMED =0;
    /**
     * 已领取
     */
    public final static Byte PRIZE_STATUS_RECEIVED=1;
    /**
     * 已过期
     */
    public final static Byte PRIZE_STATUS_EXPIRE=2;
}
