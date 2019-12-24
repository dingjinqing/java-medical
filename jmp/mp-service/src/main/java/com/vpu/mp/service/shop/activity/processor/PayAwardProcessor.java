package com.vpu.mp.service.shop.activity.processor;

import com.vpu.mp.db.shop.tables.records.OrderInfoRecord;
import com.vpu.mp.db.shop.tables.records.PayAwardRecordRecord;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueBo;
import com.vpu.mp.service.pojo.shop.coupon.give.CouponGiveQueueParam;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardContentBo;
import com.vpu.mp.service.pojo.shop.market.payaward.PayAwardVo;
import com.vpu.mp.service.pojo.shop.member.account.AccountParam;
import com.vpu.mp.service.pojo.shop.member.account.ScoreParam;
import com.vpu.mp.service.pojo.shop.operation.TradeOptParam;
import com.vpu.mp.service.pojo.wxapp.cart.activity.GoodsActivityInfo;
import com.vpu.mp.service.pojo.wxapp.order.OrderBeforeParam;
import com.vpu.mp.service.shop.coupon.CouponGiveService;
import com.vpu.mp.service.shop.market.payaward.PayAwardRecordService;
import com.vpu.mp.service.shop.market.payaward.PayAwardService;
import com.vpu.mp.service.shop.member.AccountService;
import com.vpu.mp.service.shop.member.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.PAY_AWARD_RECORD;
import static com.vpu.mp.service.foundation.data.BaseConstant.ACTIVITY_TYPE_PAY_AWARD;
import static com.vpu.mp.service.foundation.data.BaseConstant.GOODS_AREA_TYPE_SECTION;
import static com.vpu.mp.service.pojo.shop.coupon.CouponConstant.COUPON_GIVE_SOURCE_PAY_AWARD;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.GIVE_TYPE_BALANCE;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.GIVE_TYPE_CUSTOM;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.GIVE_TYPE_GOODS;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.GIVE_TYPE_LOTTERY;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.GIVE_TYPE_NO_PRIZE;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.GIVE_TYPE_ORDINARY_COUPON;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.GIVE_TYPE_SCORE;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.GIVE_TYPE_SPLIT_COUPON;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.PAY_AWARD_GIVE_STATUS_RECEIVED;
import static com.vpu.mp.service.pojo.shop.market.payaward.PayAwardConstant.PAY_AWARD_GIVE_STATUS_UNRECEIVED;
import static com.vpu.mp.service.pojo.shop.member.score.ScoreStatusConstant.NO_USE_SCORE_STATUS;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TRADE_FLOW_IN;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TYPE_CRASH_PAY_AWARD;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.TYPE_SCORE_PAY_AWARD;
import static com.vpu.mp.service.pojo.shop.operation.RecordTradeEnum.UACCOUNT_RECHARGE;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.ORDER_WAIT_DELIVERY;
import static com.vpu.mp.service.pojo.shop.order.OrderConstant.PAY_CODE_COD;
import static com.vpu.mp.service.pojo.shop.payment.PayCode.PAY_CODE_BALANCE_PAY;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

/**
 * 支付有礼活动 下单
 *
 * @author 孔德成
 * @date 2019/12/18 10:56
 */
@Service
public class PayAwardProcessor extends ShopBaseService implements Processor, CreateOrderProcessor {

    @Autowired
    private PayAwardService payAwardService;
    @Autowired
    private PayAwardRecordService payAwardRecordService;
    @Autowired
    private CouponGiveService couponGiveService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ScoreService scoreService;

    @Override
    public Byte getPriority() {
        return GoodsConstant.ACTIVITY_BARGAIN_PRIORITY;
    }

    @Override
    public Byte getActivityType() {
        return ACTIVITY_TYPE_PAY_AWARD;
    }


    /**
     * @param param
     * @throws MpException
     */
    @Override
    public void processInitCheckedOrderCreate(OrderBeforeParam param) throws MpException {

    }

    /**
     * 订单确定后调用
     * 判断保存支付有礼记录
     *
     * @param param
     * @param order
     * @throws MpException
     */
    @Override
    public void processSaveOrderInfo(OrderBeforeParam param, OrderInfoRecord order) throws MpException {
        logger().info("支付有礼活动校验");
        if (!order.getOrderStatus().equals(ORDER_WAIT_DELIVERY)||order.getPayCode().equals(PAY_CODE_COD)) {
            logger().info("不参与支付有礼活动");
            return;
        }
        //获取进行中的活动
        PayAwardVo payAward = payAwardService.getGoingPayAward(param.getDate());
        if (payAward == null) {
            logger().info("支付有礼活动为空!");
            return;
        }
        logger().info("校验支付金额是否合格");
        if (order.getOrderAmount().compareTo(payAward.getMinPayMoney()) < 0) {
            logger().info("支付金额不满足活动要求");
        }
        //活动商品
        if (payAward.getGoodsAreaType().equals(GOODS_AREA_TYPE_SECTION.intValue())) {
            boolean payAwardFlag = false;
            for (OrderBeforeParam.Goods goods : param.getGoods()) {
                boolean hasGoodsId = Arrays.asList(payAward.getGoodsIds().split(",")).contains(goods.getGoodsId().toString());
                boolean hasCatId = Arrays.asList(payAward.getGoodsCatIds().split(",")).contains(goods.getGoodsInfo().getCatId().toString());
                boolean hasSortId = Arrays.stream(payAward.getGoodsSortIds().split(",")).anyMatch(goods.getGoodsInfo().getSortId().toString()::equals);
                if (hasGoodsId || hasCatId || hasSortId) {
                    GoodsActivityInfo activityInfo = new GoodsActivityInfo();
                    activityInfo.setActivityType(ACTIVITY_TYPE_PAY_AWARD);
                    activityInfo.setActivityId(payAward.getId());
                    param.getOrderCartProductBo();
                    payAwardFlag = true;
                }
            }
            if (!payAwardFlag) {
                logger().info("支付有礼没有商品查找");
                return;
            }
        }
        logger().info("校验奖品配置");
        int payAwardSize = payAward.getAwardContentList().size();
        if (payAwardSize == 0) {
            logger().info("支付有礼没有配置奖品");
            return;
        }
        Integer joinAwardCount = payAwardRecordService.getJoinAwardCount(param.getWxUserInfo().getUserId(), payAward.getId());
        logger().info("用户:{},参与次数:{}", param.getWxUserInfo().getUserId(), joinAwardCount);
        int circleTimes = joinAwardCount / payAwardSize;
        logger().info("循环次数:{}", circleTimes);
        if (payAward.getLimitTimes() > 0 && payAward.getLimitTimes() <= circleTimes) {
            logger().info("参与次数到达上限:{}", payAward.getLimitTimes());
            return;
        }
        int currentAward = joinAwardCount % payAwardSize;
        logger().info("当前的奖励层级:{}", currentAward);
        PayAwardContentBo payAwardContentBo = payAward.getAwardContentList().get(currentAward);
        logger().info("当前奖励:" + payAwardContentBo.toString());
        if (payAwardContentBo.getGiftType().equals(GIVE_TYPE_NO_PRIZE)) {
            logger().info("当前奖励无奖品");
            return;
        }
        logger().info("礼物数量校验");
        Boolean canSendAwardFlag = payAwardRecordService.canSendAward(payAward.getId(), payAwardContentBo.getId());
        if (!canSendAwardFlag) {
            logger().info("礼物发放数量超过上限");
        }
        PayAwardRecordRecord payAwardRecordRecord = db().newRecord(PAY_AWARD_RECORD);
        payAwardRecordRecord.setAwardId(payAward.getId());
        payAwardRecordRecord.setAwardTimes(currentAward);
        payAwardRecordRecord.setUserId(param.getWxUserInfo().getUserId());
        payAwardRecordRecord.setOrderSn(order.getOrderSn());
        payAwardRecordRecord.setAwardPrizeId(payAwardContentBo.getId());
        payAwardRecordRecord.setGiftType(payAwardContentBo.getGiftType());
        // 定点杆添加支付有礼id
        order.setPayAwardId(payAward.getId());
        sendAward(param, order, payAward, payAwardContentBo, payAwardRecordRecord);
        payAwardRecordRecord.insert();
    }

    /**
     * 发送奖品
     * @param param
     * @param order
     * @param payAward
     * @param payAwardContentBo
     * @param payAwardRecordRecord
     * @throws MpException
     */
    private void sendAward(OrderBeforeParam param, OrderInfoRecord order, PayAwardVo payAward, PayAwardContentBo payAwardContentBo, PayAwardRecordRecord payAwardRecordRecord) throws MpException {
        switch (payAwardContentBo.getGiftType()) {
            case GIVE_TYPE_NO_PRIZE:
                logger().info("无奖励");
                break;
            case GIVE_TYPE_ORDINARY_COUPON:
                logger().info("奖品:优惠卷");
            case GIVE_TYPE_SPLIT_COUPON:
                logger().info("奖品:分裂优惠卷");
                List<Integer> integers = Util.stringToList(payAwardContentBo.getCouponIds());
                String[] couponArray = new String[0];
                if (integers != null) {
                    couponArray = integers.stream().map(Object::toString).toArray(String[]::new);
                }
                CouponGiveQueueParam couponGive = new CouponGiveQueueParam();
                couponGive.setUserIds(Collections.singletonList(param.getWxUserInfo().getUserId()));
                couponGive.setCouponArray(couponArray);
                couponGive.setActId(payAward.getId());
                couponGive.setAccessMode((byte) 0);
                couponGive.setGetSource(COUPON_GIVE_SOURCE_PAY_AWARD);
                /**
                 * 发送优惠卷
                 */
                CouponGiveQueueBo sendData = couponGiveService.handlerCouponGive(couponGive);
                payAwardRecordRecord.setSendData(Util.listToString(new ArrayList<>(sendData.getCouponSet())));
                payAwardRecordRecord.setAwardData(payAwardContentBo.getCouponIds());
                payAwardRecordRecord.setStatus(PAY_AWARD_GIVE_STATUS_RECEIVED);
                break;
            case GIVE_TYPE_LOTTERY:
                logger().info("幸运大抽奖");
                payAwardRecordRecord.setAwardData(payAwardContentBo.getLotteryId().toString());
                payAwardRecordRecord.setStatus(PAY_AWARD_GIVE_STATUS_UNRECEIVED);
                break;
            case GIVE_TYPE_BALANCE:
                logger().info("余额");
                AccountParam accountParam = new AccountParam() {{
                    setUserId(param.getWxUserInfo().getUserId());
                    setAmount(payAwardContentBo.getAccountNumber());
                    setOrderSn(order.getOrderSn());
                    setPayment(PAY_CODE_BALANCE_PAY);
                    setIsPaid(UACCOUNT_RECHARGE.val());
                    setRemark("支付有礼活动");
                }};
                TradeOptParam tradeOptParam = TradeOptParam.builder()
                        .tradeType(TYPE_CRASH_PAY_AWARD.val())
                        .tradeFlow(TRADE_FLOW_IN.val())
                        .build();
                accountService.updateUserAccount(accountParam, tradeOptParam);
                logger().info("余额发放完成");
                payAwardRecordRecord.setAwardData(payAwardContentBo.getAccountNumber().toString());
                payAwardRecordRecord.setSendData(payAwardContentBo.getAccountNumber().toString());
                payAwardRecordRecord.setStatus(PAY_AWARD_GIVE_STATUS_RECEIVED);
                break;
            case GIVE_TYPE_GOODS:
                logger().info("奖品");
                //TODO ...
                payAwardRecordRecord.setAwardData(payAwardContentBo.getProductId().toString());
                payAwardRecordRecord.setStatus(PAY_AWARD_GIVE_STATUS_UNRECEIVED);
                break;
            case GIVE_TYPE_SCORE:
                logger().info("积分");
                ScoreParam scoreParam = new ScoreParam();
                scoreParam.setScore(payAwardContentBo.getScoreNumber());
                scoreParam.setUserId(new Integer[]{param.getWxUserInfo().getUserId()});
                scoreParam.setOrderSn(order.getOrderSn());
                scoreParam.setScoreStatus(NO_USE_SCORE_STATUS);
                scoreService.updateMemberScore(scoreParam, INTEGER_ZERO, TYPE_SCORE_PAY_AWARD.val(), TRADE_FLOW_IN.val());
                payAwardRecordRecord.setAwardData(payAwardContentBo.getScoreNumber().toString());
                payAwardRecordRecord.setStatus(PAY_AWARD_GIVE_STATUS_RECEIVED);
                payAwardRecordRecord.setSendData(payAwardContentBo.getScoreNumber().toString());
                break;
            case GIVE_TYPE_CUSTOM:
                logger().info("自定义");
                payAwardRecordRecord.setAwardData(Util.toJson(payAwardContentBo));
                payAwardRecordRecord.setStatus(PAY_AWARD_GIVE_STATUS_UNRECEIVED);
                break;
            default:
        }
    }

    /**
     * 减库存操作
     *
     * @param param 规格id
     * @throws MpException
     */
    @Override
    public void processStockAndSales(OrderBeforeParam param) throws MpException {

    }
}
