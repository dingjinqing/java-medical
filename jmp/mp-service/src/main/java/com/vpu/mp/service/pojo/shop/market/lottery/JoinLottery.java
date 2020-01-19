package com.vpu.mp.service.pojo.shop.market.lottery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.db.shop.tables.records.LotteryPrizeRecord;
import com.vpu.mp.db.shop.tables.records.LotteryRecord;

import lombok.Data;

/**
 * 参加抽奖活动vo
 *
 * @author 孔德成
 * @date 2019/8/6 15:40
 */
@Data
public class JoinLottery {

    public static final Byte ACTIVITY_NULL= 1;
    public static final Byte ACTIVITY_STOP = 2;
    public static final Byte ACTIVITY_NOT_BEGIN = 3;
    public static final Byte ACTIVITY_OUT_DATE = 4;
    /**
     * 免费
     */
    public static final Byte FREE_PRIZE = 5;
    public static final Byte FREE_PRIZE_FINISH = 6;

    /**
     * 分享
     */
    public static final Byte SHARE_PRIZE = 7;
    /**
     * 去分享
     */
    public static final Byte SHARE_PRIZE_FINISH = 8;
    /**
     * 积分
     */
    public static final Byte SCORE_PRIZE = 9;
    /**
     * 积分抽奖次数用完
     */
    public static final Byte SCORE_PRIZE_FINISH = 10;
    /**
     * 积分不足
     */
    public static final Byte SCORE_LESS = 11;
    /**
     * 不能抽奖
     */
    public static final Byte PRIZE_FINISH = 12;


    /**
     * 抽奖状态
     * 1 活动不存在
     * 2 活动已停止
     * 3 活动未开始
     * 4 活动已过期
     * <p>
     * 5 免费抽奖，或者无限期
     * 6 免费抽奖用完
     * <p>
     * 7 使用分享抽奖
     * 8 分享抽奖次数用完，分享获取次数
     * <p>
     * 9 消耗积分抽奖
     * 10 积分抽奖次数用完
     * 11 积分不足
     * 12 抽奖次数用光啦
     */
    private Byte status;
    /**
     * 可用抽奖次数
     */
    private Integer changes;

    /**
     * 是否发送礼物
     */
    private Boolean flag=false;

    private String msg;

    private Integer score=0;

    /**
     * 抽奖规则
     */
    @JsonIgnore
    private LotteryRecord lottery;

    /**
     * 中奖奖品规则
     */
    @JsonIgnore
    private LotteryPrizeRecord lotteryPrize;

}
