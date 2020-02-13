package com.vpu.mp.service.shop.market.lottery;

import com.vpu.mp.db.shop.tables.records.LotteryPrizeRecord;
import com.vpu.mp.db.shop.tables.records.LotteryRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLottery;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.vpu.mp.db.shop.Tables.LOTTERY_PRIZE;
import static com.vpu.mp.service.pojo.shop.market.lottery.LotteryConstant.*;

/**
 * @author 孔德成
 * @date 2019/8/7 15:22
 */
@Service
public class LotteryPrizeService  extends ShopBaseService {

    private static final Random RANDOM = new Random();

    /**
     * 根据lotteryId获取最大分母
     * @param lotteryId
     * @return
     */
    public Integer getLotteryMaxDenominatorById(Integer lotteryId){
        return db().select(LOTTERY_PRIZE.CHANCE_DENOMINATOR).from(LOTTERY_PRIZE).where(LOTTERY_PRIZE.LOTTERY_ID.eq(lotteryId))
                .orderBy(LOTTERY_PRIZE.CHANCE_DENOMINATOR.desc()).fetchAnyInto(Integer.class);
    }


    public Result<LotteryPrizeRecord> getPrizeByLotteryId(Integer lotteryId) {
        return  getPrizeByLotteryId(lotteryId,(byte)0);
    }
    public Result<LotteryPrizeRecord> getPrizeByLotteryId(Integer lotteryId,Byte delFlag) {
        return  db().selectFrom(LOTTERY_PRIZE)
                .where(LOTTERY_PRIZE.LOTTERY_ID.eq(lotteryId))
                .and(LOTTERY_PRIZE.DEL_FLAG.eq(delFlag))
                .orderBy(LOTTERY_PRIZE.LOTTERY_GRADE).fetch();
    }


    /**
     * 抽奖核心逻辑
     *
     * @param joinValid  抽奖校验
     */
    void joinLotteryAction(JoinLottery joinValid) {
        LotteryRecord lottery = joinValid.getLottery();
        //取最大的分母
        Integer maxChance = getLotteryMaxDenominatorById(lottery.getId());
        int randNumber = RANDOM.nextInt(maxChance);
        Result<LotteryPrizeRecord> prizeRecords = getPrizeByLotteryId(lottery.getId());
        for (LotteryPrizeRecord record : prizeRecords) {
            int chanceNumerator = record.getChanceNumerator()*maxChance/record.getChanceDenominator();
            if (randNumber <chanceNumerator) {
                if (record.getAwardTimes() == null) {
                    record.setAwardTimes(0);
                }
                //中奖了 更新库存
                int flag= updateLotteryStock(record);
                if (flag<=0&&record.getAwardTimes() >= record.getLotteryNumber()) {
                    //奖品发完了
                    joinValid.setResultsType(LOTTERY_TYPE_SEND_OUT);
                    return;
                }
                joinValid.setResultsType(record.getLotteryType());
                joinValid.setLotteryPrize(record);
                joinValid.setLotteryGrade(record.getLotteryGrade());
                return;
            }
            randNumber -= chanceNumerator;
        }
        if (lottery.getNoAwardScore()!=null&&lottery.getNoAwardScore()>0){
            //未中奖励积分（安慰奖）
            joinValid.setResultsType(LOTTERY_TYPE_HEARTEN);
        }
        //没中奖
        joinValid.setResultsType(LOTTERY_TYPE_NULL);
    }

    /**
     * 更新奖品库存
     * @return
     */
    public int updateLotteryStock(LotteryPrizeRecord record){
        return  db().update(LOTTERY_PRIZE).set(LOTTERY_PRIZE.AWARD_TIMES, LOTTERY_PRIZE.AWARD_TIMES.add(1))
                .where(LOTTERY_PRIZE.LOTTERY_ID.eq(record.getLotteryId()))
                .and(LOTTERY_PRIZE.AWARD_TIMES.lt(LOTTERY_PRIZE.LOTTERY_NUMBER)).execute();
    }
}
