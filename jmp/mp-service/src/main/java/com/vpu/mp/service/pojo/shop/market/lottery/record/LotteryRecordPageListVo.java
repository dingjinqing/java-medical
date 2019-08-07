package com.vpu.mp.service.pojo.shop.market.lottery.record;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.service.pojo.shop.market.lottery.prize.LotteryPrizeVo;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/5 18:46
 */
@Data
public class LotteryRecordPageListVo {

    private Integer id;
    private String userName;
    private String mobile;
    private Timestamp startTime;
    private Timestamp endTime;
    /**
     * 抽奖时间
     */
    private Timestamp createTime;

    /**
     * 抽奖机会来源
     */
    private Byte chanceSource;
    /**
     * 抽奖来源
     */
    private Byte lotterySource;

    /**
     * 中奖等级
     */
    private Byte lotteryGrade;
    /**
     * 奖品信息
     */
    @JsonIgnore
    private String awardInfo;

    /**
     * 奖品信息
     */
    private LotteryPrizeVo lotteryPrize;


}
