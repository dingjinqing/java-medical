package com.vpu.mp.service.shop.market.lottery;

import static com.vpu.mp.db.shop.Tables.LOTTERY;
import static com.vpu.mp.db.shop.Tables.LOTTERY_PRIZE;
import static com.vpu.mp.db.shop.Tables.LOTTERY_RECORD;
import static com.vpu.mp.db.shop.tables.User.USER;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.vpu.mp.service.foundation.data.BaseConstant;
import org.jooq.AggregateFunction;
import org.jooq.Record7;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.LotteryPrizeRecord;
import com.vpu.mp.db.shop.tables.records.LotteryRecord;
import com.vpu.mp.db.shop.tables.records.LotteryShareRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLottery;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLotteryParam;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryPageListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryPageListVo;
import com.vpu.mp.service.pojo.shop.market.lottery.LotteryParam;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.shop.member.MemberService;

/**
 * @author 孔德成
 * @date 2019/8/5 10:55
 */
@Service
public class LotteryService extends ShopBaseService {
    private static final Byte STOP_STATUS = 0;
    private static final Byte USE_STATUS = 1;


    @Autowired
    private LotteryRecordService lotteryRecord;
    @Autowired
    private LotteryShareService lotteryShare;
    @Autowired
    private LotteryPrizeService lotteryPrize;
    @Autowired
    private MemberService member;

    /**
     * 添加幸运抽奖
     *
     * @param param 参数
     * @return lotteryId
     */
    public Integer addLottery(LotteryParam param) {
        param.setId(null);
        LotteryRecord record = db().newRecord(LOTTERY, param);
        record.insert();
        param.getPrizeList().forEach(prize -> {
            LotteryPrizeRecord prizeRecord = db().newRecord(LOTTERY_PRIZE, prize);
            prizeRecord.setId(null);
            prizeRecord.setLotteryId(record.getId());
            prizeRecord.insert();
        });
        return record.getId();
    }

    /**
     * 更新幸运抽奖
     *
     * @param param 参数 id不为空
     * @return 1成功 0 失败
     */
    public Integer updateLottery(LotteryParam param) {
        if (param.getId() != null) {
            LotteryRecord record = db().newRecord(LOTTERY, param);
            record.update();
            List<Integer> prizeIdList = new ArrayList<>();
            param.getPrizeList().forEach(prize -> {
                LotteryPrizeRecord prizeRecord = db().newRecord(LOTTERY_PRIZE, prize);
                prizeRecord.setLotteryId(record.getId());
                if (prizeRecord.getId() == null) {
                    prizeRecord.insert();
                } else {
                    prizeRecord.update();
                }
                prizeIdList.add(prizeRecord.getId());
            });
            db().update(LOTTERY_PRIZE)
                    .set(LOTTERY_PRIZE.DEL_FLAG, DelFlag.DISABLE_VALUE)
                    .where(LOTTERY_PRIZE.LOTTERY_ID.eq(record.getId()))
                    .and(LOTTERY_PRIZE.DEL_FLAG.eq(DelFlag.NORMAL_VALUE))
                    .and(LOTTERY_PRIZE.ID.notIn(prizeIdList))
                    .execute();
            return 1;
        }
        return 0;
    }

    /**
     * 停用或者启用
     *
     * @param lotteryId lotteryId
     * @return 1 成功 0失败
     */
    public Integer closeAndRestartById(Integer lotteryId) {
        LotteryRecord record = db().newRecord(LOTTERY);
        record.setId(lotteryId);
        record.refresh();
        if (STOP_STATUS.equals(record.getStatus())) {
            record.setStatus(USE_STATUS);
        } else {
            record.setStatus(STOP_STATUS);
        }
        return record.update();
    }

    /**
     * 删除活动
     *
     * @param lotteryId lotteryId
     * @return 1成功 0 失败
     */
    public Integer deleteLottery(Integer lotteryId) {
        return db().update(LOTTERY).set(LOTTERY.DEL_FLAG, DelFlag.DISABLE_VALUE).where(LOTTERY.ID.eq(lotteryId)).execute();
    }

    /**
     * 抽奖活动列表
     *
     * @param param LotteryPageListParam
     * @return PageResult<LotteryPageListVo>
     */
    public PageResult<LotteryPageListVo> getLotteryList(LotteryPageListParam param) {
        AggregateFunction<Integer> awardNumber = DSL.count(DSL.when(LOTTERY_RECORD.LOTTERY_GRADE.gt((byte) 0), LOTTERY_RECORD.ID));
        SelectConditionStep<Record7<Integer, String, Timestamp, Timestamp, Byte, Integer, Integer>> select = db()
                .select(LOTTERY.ID, LOTTERY.LOTTERY_NAME, LOTTERY.START_TIME, LOTTERY.END_TIME, LOTTERY.STATUS,
                        DSL.count(LOTTERY_RECORD.ID).as(LotteryPageListVo.JOIN_NUMBER),
                        awardNumber.as(LotteryPageListVo.AWAED_NUMBER))
                .from(LOTTERY)
                .leftJoin(LOTTERY_RECORD).on(LOTTERY.ID.eq(LOTTERY_RECORD.LOTTERY_ID))
                .where(LOTTERY.DEL_FLAG.eq(DelFlag.NORMAL_VALUE));
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        switch (param.getState()) {
            case BaseConstant.ACTIVITY_NAV_BAR_TYPE_ONGOING:
                select.and(LOTTERY.START_TIME.lt(nowTime))
                        .and(LOTTERY.END_TIME.gt(nowTime))
                        .and(LOTTERY.STATUS.eq(USE_STATUS));
                break;
            case BaseConstant.ACTIVITY_NAV_BAR_TYPE_NOT_STARTED:
                select.and(LOTTERY.STATUS.eq(USE_STATUS))
                        .and(LOTTERY.START_TIME.gt(nowTime));
                break;
            case BaseConstant.ACTIVITY_NAV_BAR_TYPE_FINISHED:
                select.and(LOTTERY.STATUS.gt(USE_STATUS))
                        .and(LOTTERY.END_TIME.lt(nowTime));
                break;
            case BaseConstant.ACTIVITY_NAV_BAR_TYPE_DISABLED:
                select.and(LOTTERY.STATUS.eq(STOP_STATUS));
                break;
            default:
        }
        select.orderBy(LOTTERY.CREATE_TIME.desc());
        select.groupBy(LOTTERY.ID, LOTTERY.LOTTERY_NAME, LOTTERY.START_TIME, LOTTERY.END_TIME,LOTTERY.STATUS);
        return getPageResult(select, param.getCurrentPage(), param.getPageRows(), LotteryPageListVo.class);
    }

    /**
     * 获取抽奖
     *
     * @param id id
     * @return record
     */
    public LotteryRecord getLotteryById(Integer id) {
        return db().selectFrom(LOTTERY).where(LOTTERY.ID.eq(id)).fetchOne();
    }


    public Result<LotteryPrizeRecord> getLotteryPrizeById(Integer id) {
        return lotteryPrize.getPrizeByLotteryId(id);
    }

    /**
     * 抽奖记录
     *
     * @param param LotteryRecordPageListParam
     * @return PageResult<LotteryRecordPageListVo>
     */
    public PageResult<LotteryRecordPageListVo> getLotteryRecordList(LotteryRecordPageListParam param) {
        return lotteryRecord.getLotteryRecordList(param);
    }

    /**
     * 获取新用户记录
     *
     * @param param MarketSourceUserListParam
     * @return PageResult<MemberInfoVo>
     */
    public PageResult<MemberInfoVo> getLotteryUserList(MarketSourceUserListParam param) {
        MemberPageListParam pageListParam = new MemberPageListParam();
        pageListParam.setCurrentPage(param.getCurrentPage());
        pageListParam.setPageRows(param.getPageRows());
        pageListParam.setMobile(param.getMobile());
        pageListParam.setUsername(param.getUserName());
        pageListParam.setInviteUserName(param.getInviteUserName());
        return saas().getShopApp(getShopId()).member.getSourceActList(pageListParam, MemberService.INVITE_SOURCE_LOTTERY, param.getActivityId());
    }

    /**
     * 参加抽奖
     *
     * @return JoinLottery
     */
    public JoinLottery joinLottery(JoinLotteryParam param) {
        //校验
        JoinLottery joinValid = this.validJoinLottery(param);
        if (!joinValid.getFlag()) {
            return joinValid;
        }
        //抽奖
        lotteryPrize.joinLotteryAction(joinValid);
        if (!joinValid.getFlag()) {
            return joinValid;
        }
        //发送奖品
        lotteryRecord.sendAwardPresent(joinValid);
        if (!joinValid.getFlag()) {
            return joinValid;
        }
        return joinValid;
    }


    /**
     * 校验是否可以抽奖，及抽奖消耗
     *
     * @param param 入参
     * @return JoinLottery
     */
    private JoinLottery validJoinLottery(JoinLotteryParam param) {
        Integer userId = param.getUserId();
        Integer lotteryId = param.getLotteryId();

        JoinLottery join = new JoinLottery();
        //获取活动
        LotteryRecord lottery = this.getLotteryById(lotteryId);
        //活动不存在了
        if (lottery == null || DelFlag.DISABLE_VALUE.equals(lottery.getDelFlag())) {
            join.setStatus(JoinLottery.ACTIVITY_NULL);
            return join;
        }
        //活动停止
        if (lottery.getStatus().equals(STOP_STATUS)) {
            join.setStatus(JoinLottery.ACTIVITY_STOP);
            return join;
        }
        //活动未开始
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        if (lottery.getStartTime().after(nowTime)) {
            join.setStatus(JoinLottery.ACTIVITY_NOT_BEGIN);
            return join;
        }
        //活动过期
        if (lottery.getEndTime().before(nowTime)) {
            join.setStatus(JoinLottery.ACTIVITY_OUT_DATE);
            return join;
        }
        //活动免费
        Integer freeTimes = lotteryRecord.getJoinLotteryNumber(userId, lotteryId, (byte) 0);
        if (lottery.getFreeChances() == null || lottery.getFreeChances() > freeTimes) {
            join.setStatus(JoinLottery.FREE_PRIZE);
            join.setFlag(true);
            if (lottery.getFreeChances() != null) {
                join.setChanges(lottery.getFreeChances() - freeTimes);
            }
            return join;
        }

        //分享抽奖
        if (lottery.getCanShare() != null && lottery.getCanShare() == (byte) 1) {
            LotteryShareRecord share = lotteryShare.getLotteryShareByUser(userId, lotteryId);
            Integer useShareTimes = lotteryRecord.getJoinLotteryNumber(userId, lotteryId, (byte) 1);
            Integer shareTimes = share != null ? share.getShareTimes() : 0;
            if (shareTimes > useShareTimes) {
                join.setStatus(JoinLottery.SHARE_PRIZE);
                join.setChanges(shareTimes - useShareTimes);
                join.setFlag(true);
                return join;
            }
            if (lottery.getShareChances() == null || shareTimes < lottery.getShareChances()) {
                join.setStatus(JoinLottery.SHARE_PRIZE_FINISH);
                if (lottery.getScoreChances() != null) {
                    join.setChanges(lottery.getShareChances() - shareTimes);
                }
                return join;
            }
        }
        //积分抽奖
        if (lottery.getCanUseScore() == (byte) 1) {
            Integer userScoreTimes = lotteryRecord.getJoinLotteryNumber(userId, lotteryId, (byte) 2);
            if (lottery.getScoreChances() == null || userScoreTimes < lottery.getScoreChances()) {
                join.setStatus(JoinLottery.SCORE_PRIZE);
                join.setFlag(true);
                join.setScore(lottery.getScorePerChance());
                if (lottery.getScoreChances() != null) {
                    join.setChanges(lottery.getScoreChances() - userScoreTimes);
                }
                Integer lotteryScore = lottery.getScorePerChance();
                Integer userScore = member.getUserFieldById(userId, USER.SCORE);
                //积分不足
                if (userScore < lotteryScore) {
                    join.setStatus(JoinLottery.SCORE_LESS);
                    join.setFlag(false);
                    return join;
                }
                return join;
            }
            join.setStatus(JoinLottery.SCORE_PRIZE_FINISH);
            return join;
        }
        join.setStatus(JoinLottery.PRIZE_FINISH);
        return join;
    }

}
