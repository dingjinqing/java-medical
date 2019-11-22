package com.vpu.mp.service.pojo.shop.market.lottery;

import com.vpu.mp.service.pojo.shop.market.lottery.prize.LotteryPrizeParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;


/**
 * @author 孔德成
 * @date 2019/8/5 11:00
 */
@Data
public class LotteryParam {

    private Integer   id;
    @NotBlank
    private String    lotteryName;
    @NotNull
    private Timestamp startTime;
    @NotNull
    private Timestamp endTime;
    @NotNull
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
    private List<LotteryPrizeParam> prizeList ;
}
