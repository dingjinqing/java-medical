package com.vpu.mp.service.pojo.shop.market.lottery;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.db.shop.tables.records.LotteryPrizeRecord;
import com.vpu.mp.db.shop.tables.records.LotteryRecord;

import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import lombok.Data;

/**
 * 参加抽奖活动vo
 *
 * @author 孔德成
 * @date 2019/8/6 15:40
 */
@Data
public class JoinLottery {

    /**
     * 是否可抽奖
     */
    private Boolean flag=false;
    /**
     * 抽奖来源类型
     */
    private Byte timeType;
    /**
     * 抽奖结果
     */
    private Byte resultsType;
    /**
     * 错误信息
     */
    private ResultMessage resultMessage;

    /**
     * 可用抽奖次数
     */
    private Integer canUseTime;

    private String msg;

    /**
     * 来源,支付,登陆
     */
    private Byte score=0;


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
