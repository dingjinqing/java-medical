package com.vpu.mp.service.pojo.shop.market.lottery;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/8/5 11:00
 */
@Data
public class LotteryParam {

    private Integer   id;
    private String    lotteryName;
    private Timestamp startTime;
    private Timestamp endTime;
    private String    lotteryExplain;
    private Integer   freeChances;
    private Byte      canShare;
    private Integer   shareChances;
    private Byte      canUseScore;
    private Integer   scorePerChance;
    private Integer   scoreChances;
    private Integer   noAwardScore;
    private String    noAwardImage;
    private String    noAwardIcon;
    private String    firstAward;
    private String    secondAward;
    private String    thirdAward;
    private String    fourthAward;
}
