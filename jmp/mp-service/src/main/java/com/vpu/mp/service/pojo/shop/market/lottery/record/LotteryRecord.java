package com.vpu.mp.service.pojo.shop.market.lottery.record;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/5 17:00
 */
@Data
public class LotteryRecord {
    private Integer   id;
    private Integer   userId;
    private Integer   lotteryId;
    private Byte      lotterySource;
    private Integer   lotteryActId;
    private Byte      chanceSource;
    private String    lotteryCost;
    private Byte      lotteryGrade;
    private Byte      lotteryType;
    private String    lotteryAward;
    private String    awardInfo;
    private Integer   prdId;
    private Byte      presentStatus;
    private String    orderSn;
    private Timestamp lotteryExpiredTime;
    private Timestamp createTime;
    private Timestamp updateTime;
}
