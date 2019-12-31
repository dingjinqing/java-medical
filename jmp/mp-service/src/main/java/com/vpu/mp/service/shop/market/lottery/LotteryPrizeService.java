package com.vpu.mp.service.shop.market.lottery;

import com.vpu.mp.db.shop.tables.records.LotteryPrizeRecord;
import com.vpu.mp.db.shop.tables.records.LotteryRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLottery;
import org.jooq.Result;
import org.jooq.SelectField;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.vpu.mp.db.shop.Tables.LOTTERY_PRIZE;

/**
 * @author 孔德成
 * @date 2019/8/7 15:22
 */
@Service
public class LotteryPrizeService  extends ShopBaseService {

    private static final Random RANDOM = new Random();
    /**
     * 根据lotteryId获取列
     * @param lotteryId 活动id
     * @param field 列
     * @param <T> 列类型
     * @return T
     */
    public <T> T getFieldById(Integer lotteryId, SelectField<T> field){
       return db().select(field).from(LOTTERY_PRIZE).where(LOTTERY_PRIZE.LOTTERY_ID.eq(lotteryId)).fetchOne().component1();
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
     * 抽奖核心
     *
     * @param joinValid  抽奖校验
     */
    void joinLotteryAction(JoinLottery joinValid) {
        LotteryRecord lottery = joinValid.getLottery();
        //取分母
        Integer maxChance = getFieldById(lottery.getId(), LOTTERY_PRIZE.CHANCE_DENOMINATOR);
        Integer randNumber = RANDOM.nextInt(maxChance);
        Result<LotteryPrizeRecord> prizeRecords = getPrizeByLotteryId(lottery.getId());
        for (LotteryPrizeRecord record : prizeRecords) {
            if (randNumber < record.getChanceNumerator()) {
                //中奖了
                if (record.getAwardTimes() >= record.getLotteryNumber()) {
                    //奖品发完了
                    joinValid.setFlag(false);
                    break;
                }
                joinValid.setFlag(true);
                joinValid.setLotteryPrize(record);
                return;
            }
            randNumber -= record.getChanceNumerator();
        }
        if (lottery.getNoAwardScore()!=null&&lottery.getNoAwardScore()>0){
            //未中奖励积分（安慰奖）
            joinValid.setFlag(true);
        }
        //没中奖
        joinValid.setFlag(false);
    }

}
