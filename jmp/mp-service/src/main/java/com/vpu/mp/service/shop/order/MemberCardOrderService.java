package com.vpu.mp.service.shop.order;

import static com.vpu.mp.db.shop.tables.CardOrder.CARD_ORDER;
import static com.vpu.mp.db.shop.tables.MemberCard.MEMBER_CARD;
import static com.vpu.mp.db.shop.tables.PaymentRecord.PAYMENT_RECORD;
import static com.vpu.mp.db.shop.tables.RefundCardRecord.REFUND_CARD_RECORD;
import static com.vpu.mp.db.shop.tables.User.USER;
import static com.vpu.mp.db.shop.tables.UserCard.USER_CARD;
import static com.vpu.mp.service.foundation.data.JsonResultMessage.INVALID_ACCOUNT_OR_SCORE;
import static com.vpu.mp.service.foundation.data.JsonResultMessage.INVALID_MONEY_AMOUNT;
import static com.vpu.mp.service.foundation.data.JsonResultMessage.INVALID_REFUND_AMOUNT;
import static com.vpu.mp.service.foundation.data.JsonResultMessage.REFUND_ACCOUNT_LARGER_THAN_ACCOUNT_PAID;
import static com.vpu.mp.service.foundation.data.JsonResultMessage.REFUND_SCORE_LARGER_THAN_SCORE_PAID;
import static com.vpu.mp.service.pojo.shop.order.virtual.MemberCardParam.SUCCESS;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;

import org.jooq.Record10;
import org.jooq.Record16;
import org.jooq.SelectOnConditionStep;
import org.springframework.stereotype.Service;

import com.vpu.mp.db.shop.tables.records.PaymentRecordRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.order.virtual.MemberCardParam;
import com.vpu.mp.service.pojo.shop.order.virtual.MemberCardRefundParam;
import com.vpu.mp.service.pojo.shop.order.virtual.MemberCardVo;

import lombok.extern.slf4j.Slf4j;

/**
 * 虚拟商品订单 - 会员卡
 *
 * @author 郑保乐
 */
@Service
@Slf4j
public class MemberCardOrderService extends ShopBaseService {

    private static final Byte GOODS_MEMBER_CARD = 0;

    /**
     * 订单列表
     */
    public PageResult<MemberCardVo> getMemberCardOrderList(MemberCardParam param) {
        SelectOnConditionStep<Record16<Integer, String, Integer, Byte, Timestamp, BigDecimal, BigDecimal, BigDecimal,
            Timestamp, String, String, String, Byte, BigDecimal, Byte, String>> select =
            db().select(CARD_ORDER.ORDER_ID, CARD_ORDER.ORDER_SN,
                CARD_ORDER.VIRTUAL_GOODS_ID, CARD_ORDER.RETURN_FLAG, CARD_ORDER.PAY_TIME, CARD_ORDER.MONEY_PAID,
                CARD_ORDER.USE_ACCOUNT, CARD_ORDER.USE_SCORE, CARD_ORDER.RETURN_TIME, USER.USERNAME, USER.MOBILE,
                MEMBER_CARD.CARD_NAME, MEMBER_CARD.CARD_TYPE, MEMBER_CARD.PAY_FEE, MEMBER_CARD.PAY_TYPE,
                USER_CARD.CARD_NO)
                .from(CARD_ORDER)
                .leftJoin(MEMBER_CARD).on(MEMBER_CARD.ID.eq(CARD_ORDER.VIRTUAL_GOODS_ID))
                .leftJoin(USER).on(CARD_ORDER.USER_ID.eq(USER.USER_ID))
                .leftJoin(USER_CARD).on(CARD_ORDER.VIRTUAL_GOODS_ID.eq(USER_CARD.CARD_ID));
        buildOptions(select, param);
        return getPageResult(select, param, MemberCardVo.class);
    }

    /**
     * 条件查询
     */
    private void buildOptions(SelectOnConditionStep<Record16<Integer, String, Integer, Byte, Timestamp, BigDecimal,
        BigDecimal, BigDecimal, Timestamp, String, String, String, Byte, BigDecimal, Byte, String>> select,
                              MemberCardParam param) {
        String orderSn = param.getOrderSn();
        String cardNo = param.getCardNo();
        Byte cardType = param.getCardType();
        String userInfo = param.getUserInfo();
        Timestamp startTime = param.getStartTime();
        Timestamp endTime = param.getEndTime();
        Boolean isRefund = param.getRefund();
        select.where(CARD_ORDER.GOODS_TYPE.eq(GOODS_MEMBER_CARD));
        if (isNotEmpty(orderSn)) {
            select.and(CARD_ORDER.ORDER_SN.like(format("%s%%", orderSn)));
        }
        if (isNotEmpty(userInfo)) {
            select.and(USER.USERNAME.like(format("%s%%", userInfo)).or(USER.MOBILE.like(format("%s%%", userInfo))));
        }
        if (isNotEmpty(cardNo)) {
            select.and(USER_CARD.CARD_NO.like(format("%s%%", cardNo)));
        }
        if (null != cardType) {
            select.and(MEMBER_CARD.CARD_TYPE.eq(cardType));
        }
        if (null != startTime) {
            select.and(CARD_ORDER.PAY_TIME.ge(startTime));
        }
        if (null != endTime) {
            select.and(CARD_ORDER.PAY_TIME.le(endTime));
        }
        if (null != isRefund) {
            if (isRefund) {
                select.and(CARD_ORDER.RETURN_FLAG.eq(SUCCESS).or(CARD_ORDER.RETURN_FLAG.eq(MemberCardParam.FAILED)));
            } else {
                select.and(CARD_ORDER.RETURN_FLAG.eq(MemberCardParam.NOT_REFUND));
            }
        }
        select.and(CARD_ORDER.DEL_FLAG.eq((byte) 0));
        select.orderBy(CARD_ORDER.ORDER_ID.desc());
    }

    /**
     * 手动退款
     */
    public void memberCardOrderRefund(MemberCardRefundParam param) {
        Double account = param.getAccount();
        Double money = param.getMoney();
        Integer score = param.getScore();
        Integer orderId = param.getOrderId();
        Record10<String, BigDecimal, BigDecimal, BigDecimal, String, Integer, Byte, BigDecimal, BigDecimal, BigDecimal>
            payInfo = getPayInfo(orderId);
        String payCode = payInfo.get(CARD_ORDER.PAY_CODE);
        BigDecimal useAccount = payInfo.get(CARD_ORDER.USE_ACCOUNT);
        BigDecimal useMoney = payInfo.get(CARD_ORDER.MONEY_PAID);
        BigDecimal useScore = payInfo.get(CARD_ORDER.USE_SCORE);
        String orderSn = payInfo.get(CARD_ORDER.ORDER_SN);
        Integer userId = payInfo.get(CARD_ORDER.USER_ID);
        BigDecimal returnAccount = payInfo.get(CARD_ORDER.RETURN_ACCOUNT);
        BigDecimal returnMoney = payInfo.get(CARD_ORDER.RETURN_MONEY);
        BigDecimal returnScore = payInfo.get(CARD_ORDER.RETURN_SCORE);
        BigDecimal availableReturnMoney = useMoney.subtract(returnMoney);
        BigDecimal availableReturnAccount = useAccount.subtract(returnAccount);
        BigDecimal availableReturnScore = useScore.subtract(returnScore);
        switch (payCode) {
            // 微信支付退款
            case MemberCardParam.PAY_WX:
                refundWxPay(orderSn, money);
                // 校验微信退款数额
                if (null == money || 0 > money || money > availableReturnMoney.doubleValue()) {
                    throw new IllegalArgumentException(INVALID_MONEY_AMOUNT);
                }
                break;
            // 余额支付
            case MemberCardParam.PAY_ACCOUNT:
                if (null == account) {
                    throw new IllegalArgumentException(INVALID_ACCOUNT_OR_SCORE);
                }
                if (0 == account) {
                    throw new IllegalArgumentException(INVALID_REFUND_AMOUNT);
                }
                if (0 != useAccount.doubleValue()) {
                    if (account > availableReturnAccount.doubleValue()) {
                        throw new IllegalArgumentException(REFUND_ACCOUNT_LARGER_THAN_ACCOUNT_PAID);
                    }
                    // 退余额
                    addUserAccount(userId, account);
                }
                break;
            // 积分支付
            case MemberCardParam.SCORE:
                if (null == score) {
                    throw new IllegalArgumentException(INVALID_ACCOUNT_OR_SCORE);
                }
                if (0 == score) {
                    throw new IllegalArgumentException(INVALID_REFUND_AMOUNT);
                }
                if (0 != useScore.doubleValue()) {
                    if (score > availableReturnScore.doubleValue()) {
                        throw new IllegalArgumentException(REFUND_SCORE_LARGER_THAN_SCORE_PAID);
                    }
                    // 退积分
                    addUserScore(userId, score);
                }
                break;
            default:
                throw new IllegalStateException("Invalid pay code: " + payCode);
        }
        BigDecimal finalReturnMoney = returnMoney.add(new BigDecimal(money));
        BigDecimal finalReturnAccount = returnAccount.add(new BigDecimal(account));
        BigDecimal finalReturnScore = returnScore.add(new BigDecimal(score));
        log.info("Member card refund -> orderId: {}, return account: {}, return money: {}, return score: {}",
            orderId, account, money, score);
        // 记录退款信息
        db().insertInto(REFUND_CARD_RECORD, REFUND_CARD_RECORD.ORDER_SN, REFUND_CARD_RECORD.USER_ID,
            REFUND_CARD_RECORD.MONEY_PAID, REFUND_CARD_RECORD.USE_ACCOUNT, REFUND_CARD_RECORD.USE_SCORE,
            REFUND_CARD_RECORD.IS_SUCCESS)
            .values(Arrays.asList(orderSn, userId, money, account, score, 1)).execute();
        // 更新订单状态
        db().update(CARD_ORDER).set(CARD_ORDER.RETURN_FLAG, SUCCESS).set(CARD_ORDER.RETURN_MONEY,
            finalReturnMoney).set(CARD_ORDER.RETURN_ACCOUNT, finalReturnAccount)
            .set(CARD_ORDER.RETURN_SCORE, finalReturnScore).execute();
    }

    /**
     * 订单信息
     */
    private Record10<String, BigDecimal, BigDecimal, BigDecimal, String, Integer, Byte, BigDecimal, BigDecimal,
        BigDecimal> getPayInfo(Integer orderId) {
        Record10<String, BigDecimal, BigDecimal, BigDecimal, String, Integer, Byte, BigDecimal, BigDecimal
            , BigDecimal> order =
            db().select(CARD_ORDER.PAY_CODE, CARD_ORDER.MONEY_PAID, CARD_ORDER.USE_SCORE, CARD_ORDER.USE_ACCOUNT,
                CARD_ORDER.ORDER_SN, CARD_ORDER.USER_ID, CARD_ORDER.RETURN_FLAG, CARD_ORDER.RETURN_MONEY,
                CARD_ORDER.RETURN_ACCOUNT, CARD_ORDER.RETURN_SCORE)
                .from(CARD_ORDER)
                .where(CARD_ORDER.ORDER_ID.eq(orderId)).fetchOne();
        if (null == order) {
            throw new IllegalArgumentException("Invalid orderId: " + orderId);
        }
        return order;
    }

    /**
     * 微信退款
     */
    private void refundWxPay(String orderSn, Double money) {
        PaymentRecordRecord payment = db().selectFrom(PAYMENT_RECORD)
            .where(PAYMENT_RECORD.ORDER_SN.eq(orderSn)).fetchAny();
        if (null == payment) {
            throw new IllegalArgumentException("Unknown order sn: " + orderSn);
        }
        log.info("WxPay refund -> orderSn: {}, amount: {}", orderSn, money);
        // todo 退款 微信支付退款 API ...
    }

    /**
     * 退积分
     */
    private void addUserScore(Integer userId, Integer score) {
        db().update(USER).set(USER.SCORE, USER.SCORE.add(score)).where(USER.USER_ID.eq(userId)).execute();
    }

    /**
     * 退余额
     */
    private void addUserAccount(Integer userId, Double account) {
        db().update(USER).set(USER.ACCOUNT, USER.ACCOUNT.add(account)).where(USER.USER_ID.eq(userId));
    }
}
