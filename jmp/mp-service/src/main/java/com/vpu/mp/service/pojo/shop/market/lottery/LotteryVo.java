package com.vpu.mp.service.pojo.shop.market.lottery;

import com.vpu.mp.service.pojo.shop.market.lottery.prize.LotteryPrizeVo;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/8/9 11:58
 */
@Data
public class LotteryVo {
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
    private Byte      status;
    private Byte      delFlag;
    private List<LotteryPrizeVo> prizeList;

}
