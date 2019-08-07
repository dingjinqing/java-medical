package com.vpu.mp.service.pojo.shop.market.lottery.record;

import com.vpu.mp.db.shop.tables.pojos.LotteryPrize;
import com.vpu.mp.service.pojo.shop.market.lottery.prize.LotteryPrizeVo;
import lombok.Data;

/**
 * @author 孔德成
 * @date 2019/8/5 15:28
 */
@Data
public class LotteryRecordPageListParam {
    /**
     * 	分页信息
     */
    private Integer currentPage;
    private Integer pageRows ;


    private Integer lotteryId;
    private String mobile;
    private String username;


    private Byte lotteryGrade;
    private Byte lotterySource;
    private Byte chanceSource;


}
