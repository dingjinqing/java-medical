package com.vpu.mp.service.pojo.shop.market.lottery;

import java.sql.Timestamp;

import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/8/5 10:58
 */
@Data
public class Lottery {

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
    private Integer   firstAwardTimes;
    private String    secondAward;
    private Integer   secondAwardTimes;
    private String    thirdAward;
    private Integer   thirdAwardTimes;
    private String    fourthAward;
    private Integer   fourthAwardTimes;
    private Byte      status;
    private Byte      delFlag;
    private Timestamp createTime;
    private Timestamp updateTime;

}
