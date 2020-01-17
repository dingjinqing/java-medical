package com.vpu.mp.service.shop.market.lottery;

import com.vpu.mp.db.shop.tables.records.LotteryShareRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.stereotype.Service;

import static com.vpu.mp.db.shop.Tables.LOTTERY_SHARE;

/**
 *  用户抽奖分享次数
 * @author 孔德成
 * @date 2019/8/6 15:21
 */
@Service
public class LotteryShareService extends ShopBaseService {


    /**
     * 获取用户抽奖活动记录
     * @param userId 用户id
     * @param lotteryId 活动id
     * @return
     */
    public LotteryShareRecord getLotteryShareByUser(Integer userId, Integer lotteryId){
        return db().fetchOne(LOTTERY_SHARE,LOTTERY_SHARE.USER_ID.eq(userId).and(LOTTERY_SHARE.LOTTERY_ID.eq(lotteryId)));
    }

}
