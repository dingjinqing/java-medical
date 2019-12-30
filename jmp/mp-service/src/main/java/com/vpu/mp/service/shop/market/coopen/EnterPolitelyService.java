package com.vpu.mp.service.shop.market.coopen;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.config.DomainConfig;
import com.vpu.mp.db.shop.tables.CoopenActivity;
import com.vpu.mp.db.shop.tables.CoopenActivityRecords;
import com.vpu.mp.db.shop.tables.records.CoopenActivityRecord;
import com.vpu.mp.db.shop.tables.records.CoopenActivityRecordsRecord;
import com.vpu.mp.db.shop.tables.records.UserRecord;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueBo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLottery;
import com.vpu.mp.service.pojo.shop.market.lottery.JoinLotteryParam;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.wxapp.market.enterpolitely.AwardVo;
import com.vpu.mp.service.pojo.wxapp.market.enterpolitely.ExtBo;
import com.vpu.mp.service.shop.coupon.CouponGiveService;
import com.vpu.mp.service.shop.market.lottery.LotteryService;
import com.vpu.mp.service.shop.member.AccountService;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.vpu.mp.service.pojo.shop.coupon.CouponConstant.COUPON_GIVE_SOURCE_PAY_AWARD;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.BYTE_THREE;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.*;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.NO_USE_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.*;
import static com.vpu.mp.service.pojo.shop.overview.OverviewConstant.STRING_ONE;
import static com.vpu.mp.service.pojo.shop.payment.PayCode.PAY_CODE_BALANCE_PAY;
import static org.apache.commons.lang3.math.NumberUtils.*;

/**
 * The type Enter politely service.
 *
 * @author liufei
 * @date 12 /23/19 开屏有礼
 */
@Service
public class EnterPolitelyService extends ShopBaseService {
    private static CoopenActivity COOPEN = CoopenActivity.COOPEN_ACTIVITY;
    private static CoopenActivityRecords RECORDS = CoopenActivityRecords.COOPEN_ACTIVITY_RECORDS;

    /**
     * The constant DEFAULT_COUPON_BG_IMG.默认发送优惠券背景图片
     *
     * @value
     */
    private static final String DEFAULT_COUPON_BG_IMG = "/image/admin/gift_config_shape.png";

    /**
     * The Order info service.
     */
    @Autowired
    private OrderInfoService orderInfoService;

    /**
     * The User service.
     */
    @Autowired
    private UserService userService;
    @Autowired
    private DomainConfig domainConfig;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private CouponGiveService couponGiveService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private JedisManager jedisManager;

    /**
     * Enter politely.
     *
     * @param userId the user id
     */
    public AwardVo enterPolitely(int userId) {
        // 开屏没有礼
        AwardVo noAward = AwardVo.builder().awardType(GIVE_TYPE_NO_PRIZE).build();
        AwardVo award;
        try {
            UserRecord userRecord = userService.getUserByUserId(userId);
            if (Objects.isNull(userRecord))
                throw new BusinessException(JsonResultCode.CODE_FAIL);
            // 获取进行中的开屏有礼活动（取优先级最高的活动）
            CoopenActivityRecord record = getProcessingActivity().stream().findFirst().orElseThrow(() -> new BusinessException(JsonResultCode.CODE_FAIL));
            int activityId = record.getId();
            noAward.setActivityId(activityId);
            // 先查缓存，key = enter_politely_shopId_activityId_userId
            String cacheKey = "enter_politely_" + getShopId() + "_" + activityId + "_" + userRecord.getUserId();
            if (StringUtils.isNotEmpty(jedisManager.get(cacheKey))) {
                return noAward;
            }
            logger().info("缓存key【{}】已失效！", cacheKey);
            // 用户是否已领取/奖品是否已发放完
            CoopenActivityRecordsRecord receiveRecord = getReceiveRecords(userId, activityId);
            if (Objects.nonNull(receiveRecord) || activityReceiveNum(activityId) >= record.getAwardNum()) {
                logger().debug("用户已领取/奖品已发放完");
                return noAward;
            }
            // 不满足新用户直接返回
            if (BYTE_ONE.equals(record.getAction()) && (Timestamp.valueOf(LocalDateTime.now()).getTime() - userRecord.getCreateTime().getTime() >= 600)) {
                logger().debug("不满足新用户");
                return noAward;
            }
            // 不满足支付新用户直接返回
            if (BYTE_THREE.equals(record.getAction()) && !orderInfoService.isNewUser(userId)) {
                logger().debug("不满足支付新用户");
                return noAward;
            }
            ExtBo coupon = ExtBo.builder().title(record.getTitle()).bgImg(imgDomain(record.getBgImgs())).build();
            switch (record.getActivityAction()) {
                case GIVE_TYPE_LOTTERY:
                    award = sendAward(GIVE_TYPE_LOTTERY, String.valueOf(record.getLotteryId()), userId, activityId, null);
                    break;
                case GIVE_TYPE_BALANCE:
                    award = sendAward(GIVE_TYPE_BALANCE, String.valueOf(record.getGiveAccount()), userId, activityId, null);
                    break;
                case GIVE_TYPE_SCORE:
                    award = sendAward(GIVE_TYPE_SCORE, String.valueOf(record.getGiveScore()), userId, activityId, null);
                    break;
                case GIVE_TYPE_CUSTOM:
                    award = sendAward(GIVE_TYPE_CUSTOM, record.getCustomizeUrl(), userId, activityId, ExtBo.builder().customizeImgPath(record.getCustomizeImgPath()).build());
                    break;
                case GIVE_TYPE_ORDINARY_COUPON:
                    award = sendAward(GIVE_TYPE_ORDINARY_COUPON, record.getMrkingVoucherId(), userId, activityId, coupon);
                    break;
                case GIVE_TYPE_SPLIT_COUPON:
                    award = sendAward(GIVE_TYPE_SPLIT_COUPON, record.getMrkingVoucherId(), userId, activityId, coupon);
                    break;
                default:
                    return noAward;
            }
            jedisManager.set(cacheKey, STRING_ONE, INTEGER_ONE);
        } catch (Throwable e) {
            logger().error("开屏有礼异常：{}", ExceptionUtils.getStackTrace(e));
            return noAward;
        }
        return award;
    }

    public AwardVo sendAward(byte awardType, String awardContent, int userId, int activityId, ExtBo bo) {
        AwardVo noAward = AwardVo.builder().activityId(activityId).awardType(GIVE_TYPE_NO_PRIZE).build();
        AwardVo award = AwardVo.builder().activityId(activityId).awardType(awardType).awardContent(awardContent).build();
        CoopenActivityRecordsRecord record = new CoopenActivityRecordsRecord();
        record.setActivityId(activityId);
        record.setUserId(userId);
        record.setActivityAction(awardType);
        switch (awardType) {
            case GIVE_TYPE_NO_PRIZE:
                logger().info("无奖励");
                break;
            case GIVE_TYPE_ORDINARY_COUPON:
                logger().info("优惠卷");
            case GIVE_TYPE_SPLIT_COUPON:
                logger().info("分裂优惠卷");
                List<Integer> integers = Util.json2Object(awardContent, new TypeReference<List<Integer>>() {
                }, false);
                String[] couponArray = new String[0];
                if (integers != null) {
                    couponArray = integers.stream().map(Object::toString).toArray(String[]::new);
                }
                CouponGiveQueueParam couponGive = new CouponGiveQueueParam();
                couponGive.setUserIds(Collections.singletonList(userId));
                couponGive.setCouponArray(couponArray);
                couponGive.setActId(activityId);
                couponGive.setAccessMode(BYTE_ZERO);
                couponGive.setGetSource(COUPON_GIVE_SOURCE_PAY_AWARD);
                // 发送优惠卷
                CouponGiveQueueBo sendData = couponGiveService.handlerCouponGive(couponGive);
                // 一张都没发成功
                if (sendData.getSuccessSize().compareTo(INTEGER_ZERO) <= INTEGER_ZERO) {
                    logger().debug("优惠券发送全部失败");
                    return noAward;
                }
                award.setExtContent(new HashMap<String, String>(INTEGER_TWO) {{
                    put("title", bo.getTitle());
                    put("bg_img", Optional.ofNullable(bo.getBgImg()).orElse(imageUrl(DEFAULT_COUPON_BG_IMG)));
                }});
                record.setMrkingVoucherId(awardContent);
                break;
            case GIVE_TYPE_LOTTERY:
                logger().info("幸运大抽奖");
                JoinLotteryParam joinLotteryParam = new JoinLotteryParam();
                joinLotteryParam.setUserId(userId);
                joinLotteryParam.setLotteryId(Integer.valueOf(awardContent));
                JoinLottery joinLottery = lotteryService.validJoinLottery(joinLotteryParam);
                if (!BYTE_ZERO.equals(joinLottery.getStatus())) {
                    logger().debug("没有可用抽奖活动");
                    return noAward;
                }
                record.setLotteryId(Integer.valueOf(awardContent));
                break;
            case GIVE_TYPE_BALANCE:
                logger().info("余额");
                AccountParam accountParam = new AccountParam() {{
                    setUserId(userId);
                    setAmount(new BigDecimal(awardContent));
                    setOrderSn(Objects.isNull(bo) ? StringUtils.EMPTY : bo.getOrderSn());
                    setPayment(PAY_CODE_BALANCE_PAY);
                    setIsPaid(UACCOUNT_RECHARGE.val());
                }};
                TradeOptParam tradeOptParam = TradeOptParam.builder()
                    .tradeType(TYPE_CRASH_PAY_AWARD.val())
                    .tradeFlow(TRADE_FLOW_IN.val())
                    .build();
                try {
                    accountService.updateUserAccount(accountParam, tradeOptParam);
                } catch (MpException e) {
                    logger().error("余额发送失败：{}", ExceptionUtils.getStackTrace(e));
                    return noAward;
                }
                logger().info("余额发放完成");
                record.setGiveNum(new BigDecimal(awardContent));
                break;
            case GIVE_TYPE_GOODS:
                logger().info("奖品");
                //TODO ...
                break;
            case GIVE_TYPE_SCORE:
                logger().info("积分");
                ScoreParam scoreParam = new ScoreParam();
                scoreParam.setScore(new BigDecimal(awardContent).intValue());
                scoreParam.setUserId(new Integer[]{userId});
                scoreParam.setOrderSn(Objects.isNull(bo) ? StringUtils.EMPTY : bo.getOrderSn());
                scoreParam.setScoreStatus(NO_USE_SCORE_STATUS);
                try {
                    scoreService.updateMemberScore(scoreParam, INTEGER_ZERO, TYPE_SCORE_PAY_AWARD.val(), TRADE_FLOW_IN.val());
                } catch (MpException e) {
                    logger().error("积分发送失败：{}", ExceptionUtils.getStackTrace(e));
                    return noAward;
                }
                logger().info("积分发放完成");
                record.setGiveNum(new BigDecimal(awardContent));
                break;
            case GIVE_TYPE_CUSTOM:
                logger().info("自定义");
                String imgPath = this.imageUrl(bo.getCustomizeImgPath());
                award.setExtContent(new HashMap<String, String>(INTEGER_ONE) {{
                    put("customize_img_path", imgPath);
                }});
                break;
            default:
                break;
        }
        db().executeInsert(record);
        return award;
    }

    /**
     * Gets processing activity.
     *
     * @return the processing activity
     */
    public List<CoopenActivityRecord> getProcessingActivity() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());
        Condition condition = COOPEN.DEL_FLAG.eq(BYTE_ZERO)
            .and(COOPEN.START_DATE.lessThan(now).and(COOPEN.END_DATE.greaterThan(now)))
            .or(COOPEN.IS_FOREVER.eq(INTEGER_ONE))
            .and(COOPEN.STATUS.eq(BYTE_ONE));
        return db().selectFrom(COOPEN).where(condition).orderBy(COOPEN.FIRST.desc(), COOPEN.CREATE_TIME.desc()).fetchInto(COOPEN);
    }

    /**
     * Gets receive records.
     *
     * @param userId     the user id
     * @param activityId the activity id
     * @return the receive records
     */
    public CoopenActivityRecordsRecord getReceiveRecords(int userId, int activityId) {
        return db().selectFrom(RECORDS).where(RECORDS.ACTIVITY_ID.eq(activityId)).and(RECORDS.USER_ID.eq(userId)).fetchOneInto(RECORDS);
    }

    /**
     * Is receive boolean.
     *
     * @param userId     the user id
     * @param activityId the activity id
     * @return the boolean
     */
    public boolean isReceive(int userId, int activityId) {
        return db().fetchExists(RECORDS, RECORDS.ACTIVITY_ID.eq(activityId).and(RECORDS.USER_ID.eq(userId)));
    }

    public int activityReceiveNum(int activityId) {
        return db().fetchCount(RECORDS, RECORDS.ACTIVITY_ID.eq(activityId));
    }

    public String imgDomain(String imgs) {
        List<String> imgList = Util.json2Object(imgs, new TypeReference<List<String>>() {
        }, false);
        if (CollectionUtils.isNotEmpty(imgList)) {
            return domainConfig.imageUrl(imgList.get(INTEGER_ZERO));
        } else {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }
    }

}
