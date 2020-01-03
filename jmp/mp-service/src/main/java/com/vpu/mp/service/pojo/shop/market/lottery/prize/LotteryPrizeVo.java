package com.vpu.mp.service.pojo.shop.market.lottery.prize;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/8/6 11:51
 */
@Data
public class LotteryPrizeVo {


    private Integer   id;
    private Integer   lotteryId;
    /**
     * 中奖率
     */
    private Integer chanceNumerator;
    private Integer chanceDenominator;
    /**
     * 中奖等级
     */
    private Byte    lotteryGrade;
    /**
     * 中奖类型
     */
    private Byte    lotteryType;
    /**
     * 奖品份数
     */
    private Integer lotteryNumber;
    /**
     * 中奖次数
     */
    private Integer awardTimes;
    /**
     * 积分数量
     */
    private Integer integralScore;
    /**
     * 账户
     */
    private Integer account;
    /**
     * 优惠券id
     */
    private Byte    couponId;
    /**
     * 赠品规格id
     */
    private Byte    prdId;
    /**
     * 赠品有效期
     */
    private Byte    prdKeepDays;
    /**
     * 奖品信息
     */
    private String  lotteryDetail;
    /**
     * 中奖图片
     */
    private String  iconImgsImage;
    /**
     * 中奖提示
     */
    private String  iconImgs;

}
