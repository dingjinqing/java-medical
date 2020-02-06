package com.vpu.mp.service.shop.market.lottery;

import com.vpu.mp.db.shop.tables.records.LotteryPrizeRecord;
import com.vpu.mp.db.shop.tables.records.LotteryRecord;
import com.vpu.mp.db.shop.tables.records.LotteryShareRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.base.ResultMessage;
import com.vpu.mp.service.pojo.shop.goods.spec.ProductSmallInfoVo;
import com.vpu.mp.service.pojo.shop.market.MarketSourceUserListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.*;
import com.vpu.mp.service.pojo.shop.market.lottery.prize.LotteryPrizeVo;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListVo;
import com.vpu.mp.service.pojo.shop.member.MemberInfoVo;
import com.vpu.mp.service.pojo.shop.member.MemberPageListParam;
import com.vpu.mp.service.pojo.wxapp.market.lottery.LotteryUserTimeInfo;
import com.vpu.mp.service.shop.goods.GoodsService;
import com.vpu.mp.service.shop.member.MemberService;
import org.jooq.AggregateFunction;
import org.jooq.Record7;
import org.jooq.Result;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.*;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.service.foundation.data.BaseConstant.*;
import static com.vpu.mp.service.pojo.shop.market.lottery.LotteryConstant.*;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * @author 孔德成
 * @date 2019/8/5 10:55
 */
@Service
public class LotteryService extends ShopBaseService {



    @Autowired
    private LotteryRecordService lotteryRecordService;
    @Autowired
    private LotteryShareService lotteryShareService;
    @Autowired
    private LotteryPrizeService lotteryPrizeService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private GoodsService goodsService;

    /**
     * 添加幸运抽奖
     *
     * @param param 参数
     * @return lotteryId
     */
    public Integer addLottery(LotteryParam param) {
        LotteryRecord record = db().newRecord(LOTTERY, param);
        record.setId(null);
        record.insert();
        param.getPrizeList().forEach(prize -> {
            LotteryPrizeRecord prizeRecord = db().newRecord(LOTTERY_PRIZE, prize);
            prizeRecord.setLotteryId(record.getId());
            prizeRecord.setAwardTimes(0);
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
        if (ACTIVITY_STATUS_NORMAL.equals(record.getStatus())) {
            record.setStatus(ACTIVITY_STATUS_DISABLE);
        } else {
            record.setStatus(ACTIVITY_STATUS_NORMAL);
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
            case BaseConstant.NAVBAR_TYPE_ONGOING:
                select.and(LOTTERY.START_TIME.lt(nowTime))
                        .and(LOTTERY.END_TIME.gt(nowTime))
                        .and(LOTTERY.STATUS.eq(ACTIVITY_STATUS_NORMAL));
                break;
            case BaseConstant.NAVBAR_TYPE_NOT_STARTED:
                select.and(LOTTERY.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                        .and(LOTTERY.START_TIME.gt(nowTime));
                break;
            case BaseConstant.NAVBAR_TYPE_FINISHED:
                select.and(LOTTERY.STATUS.eq(ACTIVITY_STATUS_NORMAL))
                        .and(LOTTERY.END_TIME.lt(nowTime));
                break;
            case BaseConstant.NAVBAR_TYPE_DISABLED:
                select.and(LOTTERY.STATUS.eq(ACTIVITY_STATUS_DISABLE));
                break;
            default:
        }
        select.orderBy(LOTTERY.CREATE_TIME.desc());
        select.groupBy(LOTTERY.ID, LOTTERY.LOTTERY_NAME, LOTTERY.START_TIME, LOTTERY.END_TIME,LOTTERY.STATUS);
        PageResult<LotteryPageListVo> res = getPageResult(select, param.getCurrentPage(), param.getPageRows(), LotteryPageListVo.class);
        res.dataList.forEach(vo -> {
            vo.setCurrentState(Util.getActStatus(vo.getStatus(),vo.getStartTime(),vo.getEndTime()));
        });
        return res;
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

    /**
     * 获取活动及奖品初始化
     * @param id 活动id
     * @return LotteryVo or null
     */
    public LotteryVo getLotteryVo(Integer id){
        LotteryRecord lottery = getLotteryById(id);
        Result<LotteryPrizeRecord> lotteryPrizeList = getLotteryPrizeById(id);
        if (lottery==null||lotteryPrizeList.size()==0){
            return null;
        }
        LotteryVo lotteryVo =lottery.into(LotteryVo.class);
        List<LotteryPrizeVo>  lotteryPrizeVoList =lotteryPrizeList.into(LotteryPrizeVo.class);
        lotteryPrizeVoList.forEach( lotteryPrizeVo -> {
            if (lotteryPrizeVo.getLotteryType().equals(LotteryConstant.LOTTERY_TYPE_GOODS)){
                ProductSmallInfoVo product =  goodsService.getProductVoInfoByProductId(lotteryPrizeVo.getPrdId());
                lotteryPrizeVo.setProduct(product);
            }
        });
        lotteryVo.setPrizeList(lotteryPrizeVoList);
        return lotteryVo;
    }

    /**
     * Fetch available lottery lottery record.获取可用抽奖活动
     *
     * @param id the id
     * @return the lottery record
     */
    public LotteryRecord fetchAvailableLottery(Integer id) {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        return db().selectFrom(LOTTERY)
            .where(LOTTERY.ID.eq(id))
            .and(LOTTERY.DEL_FLAG.eq(BYTE_ZERO))
            .and(LOTTERY.STATUS.eq(BYTE_ONE))
            .and(LOTTERY.END_TIME.greaterThan(now))
            .and(LOTTERY.START_TIME.lessThan(now))
            .fetchOne();
    }


    public Result<LotteryPrizeRecord> getLotteryPrizeById(Integer id) {
        return lotteryPrizeService.getPrizeByLotteryId(id);
    }

    /**
     * 抽奖记录
     *
     * @param param LotteryRecordPageListParam
     * @return PageResult<LotteryRecordPageListVo>
     */
    public PageResult<LotteryRecordPageListVo> getLotteryRecordList(LotteryRecordPageListParam param) {
        return lotteryRecordService.getLotteryRecordList(param);
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
        //校验,扣积分
        JoinLottery joinValid = this.validJoinLottery(param);
        if (!joinValid.getFlag()) {
            return joinValid;
        }
        //抽奖
        lotteryPrizeService.joinLotteryAction(joinValid);
        //发送奖品 记录
        try {
            lotteryRecordService.sendAwardPresent(param.getUserId(),joinValid);
        } catch (MpException e) {
            e.printStackTrace();
        }
        return joinValid;
    }


    /**
     * 校验是否可以抽奖，及抽奖消耗
     *
     * @param param 入参
     * @return JoinLottery
     */
    public JoinLottery validJoinLottery(JoinLotteryParam param) {
        Integer userId = param.getUserId();
        Integer lotteryId = param.getLotteryId();

        JoinLottery join = new JoinLottery();
        //获取活动
        LotteryRecord lottery = this.getLotteryById(lotteryId);
        join.setLottery(lottery);
        //活动不存在了
        if (lottery == null || DelFlag.DISABLE_VALUE.equals(lottery.getDelFlag())) {
            join.setResultMessage(ResultMessage.builder().jsonResultCode(JsonResultCode.LOTTERY_ACTIVITY_FAIL).build());
            return join;
        }
        //活动停止
        if (lottery.getStatus().equals(ACTIVITY_STATUS_DISABLE)) {
            join.setResultMessage(ResultMessage.builder().jsonResultCode(JsonResultCode.LOTTERY_ACTIVITY_STOP).build());
            return join;
        }
        //活动未开始
        Timestamp nowTime = new Timestamp(System.currentTimeMillis());
        if (lottery.getStartTime().after(nowTime)) {
            join.setResultMessage(ResultMessage.builder().jsonResultCode(JsonResultCode.LOTTERY_ACTIVITY_NOT_BEGIN).build());
            return join;
        }
        //活动过期
        if (lottery.getEndTime().before(nowTime)) {
            join.setResultMessage(ResultMessage.builder().jsonResultCode(JsonResultCode.LOTTERY_ACTIVITY_OUT_DATE).build());
            return join;
        }
        //活动免费
        Integer freeTimes = lotteryRecordService.getJoinLotteryNumber(userId, lotteryId,LOTTERY_TIME_FREE);
        if (lottery.getFreeChances() != null && lottery.getFreeChances() > freeTimes) {
            join.setChanceSource(LOTTERY_TIME_FREE);
            join.setFlag(true);
            if (lottery.getFreeChances() != null) {
                join.setCanUseTime(lottery.getFreeChances() - freeTimes);
            }
            return join;
        }
        //分享抽奖
        if (lottery.getCanShare() != null && lottery.getCanShare().equals(YES)) {
            LotteryShareRecord shareRecord = lotteryShareService.getLotteryShareByUser(userId, lotteryId);
            //分享次数
            Integer shareTimes = shareRecord != null ? shareRecord.getShareTimes() : 0;
            Integer usedShareTimes= shareRecord != null ? shareRecord.getUseShareTimes() : 0;
            if (shareTimes >usedShareTimes) {
                //分享抽奖
                join.setChanceSource(LOTTERY_TIME_SHARE);
                join.setCanUseTime(shareTimes - usedShareTimes);
                join.setFlag(true);
                return join;
            }
            // 去分享
            if (lottery.getShareChances() != null && shareTimes < lottery.getShareChances()) {
                join.setResultMessage(ResultMessage.builder().jsonResultCode(JsonResultCode.LOTTERY_SHARE_TIME_USE_UP)
                        .message(lottery.getShareChances()-shareTimes).build());
                return join;
            }
        }
        //积分抽奖
        if (lottery.getCanUseScore().equals(YES)) {
            Integer userScoreTimes = lotteryRecordService.getJoinLotteryNumber(userId, lotteryId, LOTTERY_TIME_SCORE);
            if (lottery.getScoreChances() != null && userScoreTimes < lottery.getScoreChances()) {
                join.setChanceSource(LOTTERY_TIME_SCORE);
                join.setFlag(true);
                join.setSource(lottery.getScorePerChance().byteValue());
                if (lottery.getScoreChances() != null) {
                    join.setCanUseTime(lottery.getScoreChances() - userScoreTimes);
                }
                Integer lotteryScore = lottery.getScorePerChance();
                Integer userScore = memberService.getUserFieldById(userId, USER.SCORE);
                //积分不足
                if (userScore < lotteryScore) {
                    join.setResultMessage(ResultMessage.builder().jsonResultCode(JsonResultCode.LOTTERY_ACTIVITY_OUT_DATE).build());
                    join.setFlag(false);
                    return join;
                }
                return join;
            }
        }
        //抽奖次数用完
        join.setFlag(false);
        join.setResultMessage(ResultMessage.builder().jsonResultCode(JsonResultCode.LOTTERY_TIME_USE_UP).build());
        return join;
    }

    /**
     * 用户抽奖详情
     * @param userId
     * @param lotteryId
     * @return
     */
    public LotteryUserTimeInfo getUserLotteryInfo(Integer userId, Integer lotteryId) {
        LotteryRecord lottery = getLotteryById(lotteryId);
        LotteryUserTimeInfo lotteryTimeInfo =new LotteryUserTimeInfo();
        //全部
        Integer usedAllTime = lotteryRecordService.getJoinLotteryNumber(userId, lotteryId, LOTTERY_TIME_ALL);
        lotteryTimeInfo.setUsedTime(usedAllTime);
        //免费
        lotteryTimeInfo.setFreeTime(lottery.getFreeChances());
        Integer usedFreeTime = lotteryRecordService.getJoinLotteryNumber(userId, lotteryId, LOTTERY_TIME_FREE);
        lotteryTimeInfo.setUsedFreeTime(usedFreeTime);
        //分享
        if (lottery.getCanShare().equals(YES)){
            LotteryShareRecord lotteryShareByUser = lotteryShareService.getLotteryShareByUser(userId, lotteryId);
            if (lotteryShareByUser!=null){
                lotteryTimeInfo.setShareMaximum(lottery.getShareChances());
                lotteryTimeInfo.setShareTime(lotteryShareByUser.getShareTimes());
                lotteryTimeInfo.setUsedShareTime(lotteryShareByUser.getUseShareTimes());
            }
        }
        //积分
        if (lottery.getCanUseScore().equals(YES)){
            Integer usedScoreTime = lotteryRecordService.getJoinLotteryNumber(userId, lotteryId, LOTTERY_TIME_SCORE);
            lotteryTimeInfo.setScoreMaximum(lottery.getScoreChances());
            lotteryTimeInfo.setUsedScoreTime(usedScoreTime);
            lotteryTimeInfo.setScore(lottery.getScorePerChance());
        }
        return lotteryTimeInfo;
    }

    /**
     * 分享抽奖活动
     * @param userId
     * @param lotteryId
     */
    public void shareLottery(Integer userId, Integer lotteryId) {
        lotteryShareService.addShareRecord(userId,lotteryId);
    }
}
