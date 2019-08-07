package com.vpu.mp.service.shop.market.lottery;

import com.vpu.mp.db.shop.Tables;
import com.vpu.mp.db.shop.tables.records.LotteryRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLottery;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLotteryParam;
import com.vpu.mp.service.pojo.shop.market.lottery.prize.LotteryPrizeVo;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListVo;
import com.vpu.mp.service.shop.member.MemberService;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

import static com.vpu.mp.db.shop.Tables.LOTTERY;
import static com.vpu.mp.db.shop.Tables.LOTTERY_PRIZE;
import static com.vpu.mp.db.shop.tables.LotteryRecord.LOTTERY_RECORD;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.service.pojo.shop.market.lottery.JoinLotteryParam.*;

/**
 * @author 孔德成
 * @date 2019/8/6 9:34
 */
@Service
public class LotteryRecordService extends ShopBaseService {

    @Autowired
    private MemberService member;

    /**
     * 抽奖记录查询
     *
     * @param param
     * @return
     */
    public PageResult<LotteryRecordPageListVo> getLotteryRecordList(LotteryRecordPageListParam param) {
        SelectConditionStep<Record> select = db().select(LOTTERY_RECORD.asterisk(), USER.USERNAME, USER.MOBILE)
                .from(LOTTERY_RECORD)
                .leftJoin(USER).on(USER.USER_ID.eq(LOTTERY_RECORD.USER_ID))
                .where(LOTTERY_RECORD.LOTTERY_ID.eq(param.getLotteryId()));
        if (param.getUsername() != null) {
            select.and(Tables.USER.USERNAME.like(likeValue(param.getUsername())));
        }
        if (param.getMobile() != null) {
            select.and(Tables.USER.MOBILE.like(prefixLikeValue(param.getMobile())));
        }
        if (param.getLotteryGrade() != null) {
            select.and(Tables.LOTTERY_RECORD.LOTTERY_GRADE.eq(param.getLotteryGrade()));
        }
        if (param.getLotteryGrade() != null) {
            select.and(Tables.LOTTERY_RECORD.LOTTERY_SOURCE.eq(param.getLotterySource()));
        }
        if (param.getChanceSource() != null) {
            select.and(Tables.LOTTERY_RECORD.CHANCE_SOURCE.eq(param.getChanceSource()));
        }
        select.orderBy(LOTTERY_RECORD.CREATE_TIME.desc());
        PageResult<LotteryRecordPageListVo> pageList = getPageResult(select, param.getCurrentPage(), param.getPageRows(), LotteryRecordPageListVo.class);
        pageList.getDataList().forEach(item -> {
            item.setLotteryPrize(Util.parseJson(item.getAwardInfo(), LotteryPrizeVo.class));
            item.setAwardInfo(null);
        });
        return pageList;

    }


    /**
     * 获取用户已抽奖次数
     * @param userId
     * @param lotteryId
     * @param chanceSource 0:free 1:share 2:score
     * @return
     */
    public Integer getJoinLotteryNumber(Integer userId, Integer lotteryId, Byte chanceSource) {
        Condition condition = LOTTERY_RECORD.USER_ID.eq(userId)
                .and(LOTTERY_RECORD.LOTTERY_ID.eq(lotteryId));
        if (chanceSource > -1) {
            condition.and(LOTTERY_RECORD.CHANCE_SOURCE.eq(chanceSource));
        }
        return db().fetchCount(LOTTERY_RECORD, condition);
    }

    /**
     * 获取用户已抽奖次数
     *
     * @param userId
     * @param lotteryId
     * @return
     */
    public Integer getJoinLotteryNumber(Integer userId, Integer lotteryId) {
        return getJoinLotteryNumber(userId, lotteryId, (byte) -1);
    }




}
