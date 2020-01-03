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
import com.vpu.mp.service.pojo.shop.coupon.CouponView;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueBo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.wxapp.market.enterpolitely.AwardVo;
import com.vpu.mp.service.pojo.wxapp.market.enterpolitely.ExtBo;
import com.vpu.mp.service.shop.coupon.CouponGiveService;
import com.vpu.mp.service.shop.coupon.CouponService;
import com.vpu.mp.service.shop.market.lottery.LotteryService;
import com.vpu.mp.service.shop.member.AccountService;
import com.vpu.mp.service.shop.member.ScoreService;
import com.vpu.mp.service.shop.order.info.OrderInfoService;
import com.vpu.mp.service.shop.user.user.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static com.vpu.mp.service.pojo.shop.coupon.CouponConstant.COUPON_GIVE_SOURCE_PAY_AWARD;
import static com.vpu.mp.service.pojo.shop.market.increasepurchase.PurchaseConstant.BYTE_THREE;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.GIVE_TYPE_NO_PRIZE;
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

    @Autowired
    private CouponService couponService;

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
            if (Objects.isNull(userRecord)) {
                throw new BusinessException(JsonResultCode.CODE_FAIL);
            }
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
            ExtBo coupon = ExtBo.builder().title(record.getTitle()).bgImg(this.imgDomain(DEFAULT_COUPON_BG_IMG)).build();
            switch (record.getActivityAction()) {
                case 2:
                    award = sendAward((byte) 2, String.valueOf(record.getLotteryId()), userId, activityId, null);
                    break;
                case 5:
                    award = sendAward((byte) 5, String.valueOf(record.getGiveAccount()), userId, activityId, null);
                    break;
                case 4:
                    award = sendAward((byte) 4, String.valueOf(record.getGiveScore()), userId, activityId, null);
                    break;
                case 3:
                    award = sendAward((byte) 3, record.getCustomizeUrl(), userId, activityId, ExtBo.builder().customizeImgPath(record.getCustomizeImgPath()).build());
                    break;
                case 1:
                    award = sendAward((byte) 1, record.getMrkingVoucherId(), userId, activityId, coupon);
                    break;
                case 6:
                    award = sendAward((byte) 6, record.getMrkingVoucherId(), userId, activityId, coupon);
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

    /**
     * Send award award vo.
     *
     * @param awardType    the award type
     * @param awardContent the award content
     * @param userId       the user id
     * @param activityId   the activity id
     * @param bo           the bo
     * @return the award vo
     */
    public AwardVo sendAward(byte awardType, String awardContent, int userId, int activityId, ExtBo bo) {
        AwardVo noAward = AwardVo.builder().activityId(activityId).awardType(GIVE_TYPE_NO_PRIZE).build();
        AwardVo award = AwardVo.builder().activityId(activityId).awardType(awardType).awardContent(awardContent).build();
        CoopenActivityRecordsRecord record = new CoopenActivityRecordsRecord();
        record.setActivityId(activityId);
        record.setUserId(userId);
        record.setActivityAction(awardType);
        switch (awardType) {
            case 0:
                logger().info("无奖励");
                break;
            case 1:
                logger().info("优惠卷");
            case 6:
                logger().info("分裂优惠卷");
                String[] couponArray = awardContent.split(",");
                List<CouponView> couponViews = couponService.getCouponViewByIds(Util.stringList2IntList(Arrays.asList(couponArray)));
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
                    put("coupon_detail", Util.toJson(couponViews));
                    put("bg_img", StringUtils.isBlank(bo.getBgImg()) ? imageUrl(DEFAULT_COUPON_BG_IMG) : bo.getBgImg());
                }});
                record.setMrkingVoucherId(awardContent);
                break;
            case 2:
                logger().info("幸运大抽奖");
                record.setLotteryId(Integer.valueOf(awardContent));
                logger().debug("抽奖活动id：{}", awardContent);
                break;
            case 5:
                logger().info("余额");
                AccountParam accountParam = new AccountParam() {{
                    setUserId(userId);
                    setAmount(NumberUtils.createBigDecimal(awardContent));
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
            case 7:
                logger().info("奖品");
                //TODO ...
                break;
            case 4:
                logger().info("积分");
                ScoreParam scoreParam = new ScoreParam();
                scoreParam.setScore(NumberUtils.createBigDecimal(awardContent).intValue());
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
            case 3:
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
