package com.vpu.mp.service.shop.market.lottery;

import com.vpu.mp.db.shop.tables.records.LotteryPrizeRecord;
import com.vpu.mp.db.shop.tables.records.LotteryRecord;
import com.vpu.mp.db.shop.tables.records.LotteryRecordRecord;
import com.vpu.mp.db.shop.tables.records.PrizeRecordRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueBo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLottery;
import com.vpu.mp.service.pojo.shop.market.lottery.prize.LotteryPrizeVo;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListParam;
import com.vpu.mp.service.pojo.shop.market.lottery.record.LotteryRecordPageListVo;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.operation.RemarkTemplate;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.shop.coupon.CouponGiveService;
import com.vpu.mp.service.shop.image.ImageService;
import com.vpu.mp.service.shop.market.prize.PrizeRecordService;
import com.vpu.mp.service.shop.member.AccountService;
import com.vpu.mp.service.shop.member.MemberService;
import com.vpu.mp.service.shop.member.ScoreService;
import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.SelectOnConditionStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static com.vpu.mp.db.shop.tables.LotteryRecord.LOTTERY_RECORD;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.service.pojo.shop.coupon.CouponConstant.COUPON_GIVE_SOURCE_LOTTERY_AWARD;
import static com.vpu.mp.service.pojo.shop.coupon.CouponConstant.COUPON_GIVE_SOURCE_PAY_AWARD;
import static com.vpu.mp.service.pojo.shop.market.lottery.LotteryConstant.*;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.NO_USE_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.*;
import static com.vpu.mp.service.pojo.shop.payment.PayCode.PAY_CODE_BALANCE_PAY;
import static com.vpu.mp.service.pojo.wxapp.market.prize.PrizeConstant.PRIZE_SOURCE_LOTTERY;
import static com.vpu.mp.service.pojo.wxapp.market.prize.PrizeConstant.PRIZE_SOURCE_PAY_AWARD;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

/**
 * 抽奖记录
 *
 * @author 孔德成
 * @date 2019/8/6 9:34
 */
@Service
public class LotteryRecordService extends ShopBaseService {

    @Autowired
    private MemberService member;
    @Autowired
    private ScoreService scoreService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private PrizeRecordService prizeRecordService;
    @Autowired
    private CouponGiveService couponGiveService;
    @Autowired
    private ImageService imageService;

    /**
     * 抽奖记录查询
     *
     * @param param LotteryRecordPageListParam
     * @return LotteryRecordPageListVo
     */
    public PageResult<LotteryRecordPageListVo> getLotteryRecordList(LotteryRecordPageListParam param) {
        SelectOnConditionStep<Record> select = db().select(LOTTERY_RECORD.asterisk(), USER.USERNAME, USER.MOBILE)
                .from(LOTTERY_RECORD)
                .leftJoin(USER).on(USER.USER_ID.eq(LOTTERY_RECORD.USER_ID));
        buildSelect(select, param);
        select.orderBy(LOTTERY_RECORD.CREATE_TIME.desc());
        PageResult<LotteryRecordPageListVo> pageList = getPageResult(select, param.getCurrentPage(), param.getPageRows(), LotteryRecordPageListVo.class);
        pageList.getDataList().forEach(item -> {
            item.setLotteryPrize(Util.parseJson(item.getAwardInfo(), LotteryPrizeVo.class));
            item.setAwardInfo(null);
        });
        return pageList;
    }

    private void buildSelect(SelectOnConditionStep<Record> select, LotteryRecordPageListParam param) {
        if (param.getLotteryId() != null) {
            select.where(LOTTERY_RECORD.LOTTERY_ID.eq(param.getLotteryId()));
        }
        if (param.getUsername() != null) {
            select.where(USER.USERNAME.like(likeValue(param.getUsername())));
        }
        if (param.getMobile() != null) {
            select.where(USER.MOBILE.like(prefixLikeValue(param.getMobile())));
        }
        if (param.getLotteryGrade() != null && param.getLotteryGrade() >= 0) {
            select.where(LOTTERY_RECORD.LOTTERY_GRADE.eq(param.getLotteryGrade()));
        }
        if (param.getLotterySource() != null && param.getLotterySource() >= 0) {
            select.where(LOTTERY_RECORD.LOTTERY_SOURCE.eq(param.getLotterySource()));
        }
        if (param.getChanceSource() != null && param.getChanceSource() >= 0) {
            select.where(LOTTERY_RECORD.CHANCE_SOURCE.eq(param.getChanceSource()));
        }
        if (param.getLotteryActId() != null) {
            select.where(LOTTERY_RECORD.LOTTERY_ACT_ID.eq(param.getLotteryActId()));

        }
    }


    /**
     * 获取用户已抽奖次数
     *
     * @param userId       会员id
     * @param lotteryId    活动id
     * @param chanceSource 1:free 2:share 3:score
     * @return 抽奖次数
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
     * 发送奖品
     *
     * @param userId
     * @param joinValid JoinLottery
     */
    public void sendAwardPresent(Integer userId, JoinLottery joinValid) throws MpException {
        LotteryPrizeRecord lotteryPrizeRecord = joinValid.getLotteryPrize();
        LotteryRecord lotteryRecord = joinValid.getLottery();
        LotteryRecordRecord recordRecord = db().newRecord(LOTTERY_RECORD);
        recordRecord.setUserId(userId);
        recordRecord.setLotteryId(lotteryRecord.getId());
        recordRecord.setLotteryActId(lotteryRecord.getId());
        recordRecord.setLotterySource(joinValid.getSource());
        recordRecord.setLotteryType(joinValid.getResultsType());
        recordRecord.setChanceSource((byte) 1);
        recordRecord.setPrdId(0);
        recordRecord.setLotteryGrade(lotteryPrizeRecord != null ? lotteryPrizeRecord.getLotteryGrade() : 0);
        recordRecord.setLotteryAward(lotteryPrizeRecord != null ? lotteryPrizeRecord.getLotteryDetail() : "");
        recordRecord.setAwardInfo(lotteryRecord.toString());

        joinValid.setPrizeImage(imageService.getImgFullUrl(lotteryPrizeRecord != null ? lotteryPrizeRecord.getIconImgsImage() : null));
        joinValid.setPrizeText(lotteryPrizeRecord!=null?lotteryPrizeRecord.getIconImgs():"");
        logger().info("抽奖结果:");
        //选择奖类型
        switch (joinValid.getResultsType()) {
            case LOTTERY_TYPE_NULL:
                logger().info("未中奖");
                recordRecord.setLotteryGrade((byte) 0);
                recordRecord.setLotteryAward(lotteryRecord.getNoAwardIcon());
                joinValid.setPrizeImage(imageService.getImgFullUrl(lotteryRecord.getNoAwardImage()));
                joinValid.setPrizeText(lotteryRecord.getNoAwardIcon());
                break;
            case LOTTERY_TYPE_SEND_OUT:
                logger().info("已经发完了");
                recordRecord.setLotteryGrade((byte) 0);
                recordRecord.setLotteryAward(lotteryRecord.getNoAwardIcon());
                joinValid.setPrizeImage(imageService.getImgFullUrl(lotteryRecord.getNoAwardImage()));
                joinValid.setPrizeText(lotteryRecord.getNoAwardIcon());
                break;
            case LOTTERY_TYPE_HEARTEN:
                logger().info("安慰奖");
                recordRecord.setLotteryGrade((byte) 0);
                recordRecord.setLotteryAward("未中奖赠送积分:"+lotteryRecord.getNoAwardScore());
                recordRecord.setAwardInfo(Util.toJson(lotteryRecord));
                recordRecord.setPresentStatus(LOTTERY_PRIZE_STATUS_RECEIVED);

                joinValid.setPrizeImage(imageService.getImgFullUrl(lotteryPrizeRecord.getIconImgsImage()));
                joinValid.setPrizeText(lotteryRecord.getNoAwardIcon());
                ScoreParam scoreParam = new ScoreParam();
                scoreParam.setScore(lotteryRecord.getNoAwardScore());
                scoreParam.setUserId(userId);
                scoreParam.setScoreStatus(NO_USE_SCORE_STATUS);
                scoreService.updateMemberScore(scoreParam, INTEGER_ZERO, TYPE_SCORE_LOTTERY.val(), TRADE_FLOW_IN.val());
                break;
            case LOTTERY_TYPE_SCORE:
                logger().info("积分");
                recordRecord.setPresentStatus(LOTTERY_PRIZE_STATUS_RECEIVED);

                scoreParam = new ScoreParam();
                scoreParam.setScore(lotteryRecord.getNoAwardScore());
                scoreParam.setUserId(userId);
                scoreParam.setScoreStatus(NO_USE_SCORE_STATUS);
                scoreService.updateMemberScore(scoreParam, INTEGER_ZERO, TYPE_SCORE_LOTTERY.val(), TRADE_FLOW_IN.val());
                break;
            case LOTTERY_TYPE_BALANCE:
                logger().info("用户余额");
                recordRecord.setPresentStatus(LOTTERY_PRIZE_STATUS_RECEIVED);
                AccountParam accountParam = new AccountParam() {{
                    setUserId(userId);
                    setAmount(BigDecimal.ZERO);
                    setPayment(PAY_CODE_BALANCE_PAY);
                    setIsPaid(UACCOUNT_RECHARGE.val());
                    setRemarkId(RemarkTemplate.MSG_LOTTERY_GIFT.code);
                }};
                TradeOptParam tradeOptParam = TradeOptParam.builder()
                        .tradeType(TYPE_CRASH_LOTTERY.val())
                        .tradeFlow(TRADE_FLOW_IN.val())
                        .build();
                accountService.updateUserAccount(accountParam, tradeOptParam);
                break;
            case LOTTERY_TYPE_COUPON:
                logger().info("优惠卷");
                recordRecord.setPresentStatus(LOTTERY_PRIZE_STATUS_RECEIVED);
                CouponGiveQueueParam couponGive = new CouponGiveQueueParam();
                couponGive.setUserIds(Collections.singletonList(userId));
                couponGive.setCouponArray(new String[]{String.valueOf(lotteryPrizeRecord.getCouponId())});
                couponGive.setActId(lotteryRecord.getId());
                couponGive.setAccessMode((byte) 0);
                couponGive.setGetSource(COUPON_GIVE_SOURCE_LOTTERY_AWARD);
                /**
                 * 发送优惠卷
                 */
                CouponGiveQueueBo sendData = couponGiveService.handlerCouponGive(couponGive);
                break;
            case LOTTERY_TYPE_GOODS:
                logger().info("赠品");
                recordRecord.setPrdId(lotteryPrizeRecord.getPrdId());
                recordRecord.setPresentStatus(LOTTERY_PRIZE_STATUS_UNCLAIMED);
                Timestamp timeStampPlus = DateUtil.getTimeStampPlus(lotteryPrizeRecord.getPrdKeepDays().intValue(), ChronoUnit.DAYS);
                recordRecord.setLotteryExpiredTime(timeStampPlus);
                recordRecord.insert();

                PrizeRecordRecord prizeRecordRecord = prizeRecordService.savePrize(userId, lotteryRecord.getId(), recordRecord.getId(),
                        PRIZE_SOURCE_LOTTERY, lotteryPrizeRecord.getPrdId(), lotteryPrizeRecord.getPrdKeepDays().intValue());
                break;
            case LOTTERY_TYPE_CUSTOM:
                logger().info("自定义");

                break;
            default:
        }
        if (recordRecord.getId() == null) {
            recordRecord.insert();
        }
    }


}
