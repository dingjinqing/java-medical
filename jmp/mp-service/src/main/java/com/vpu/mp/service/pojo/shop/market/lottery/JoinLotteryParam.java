package com.vpu.mp.service.pojo.shop.market.lottery;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/8/6 17:10
 */
@Data
public class JoinLotteryParam {

    /**
     * 分享
     */
    public static final byte SHARE =1;
    /**
     * 积分
     */
    public static final byte SCORE =2;
    /**
     * 免费
     */
    public static final byte FREE =3;


    private Integer userId;
    private Integer lotteryId;

    /**
     * 抽奖类型 share
     *          score
     *          free
     */
    private Byte lotteryType;



}
