package com.vpu.mp.service.shop.task.overview;

import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.jooq.Condition;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

import static com.vpu.mp.service.foundation.util.BigDecimalUtil.BIGDECIMAL_ZERO;
import static com.vpu.mp.service.shop.task.overview.GoodsStatisticTaskService.*;

/**
 * The type User summary task service.
 *
 * @author liufei
 * @date 12 /11/19 用户相关统计信息
 */
@Service
public class UserSummaryTaskService extends ShopBaseService {
    /**
     * The constant LOGIN.
     */
    public static final UserLoginRecord LOGIN = UserLoginRecord.USER_LOGIN_RECORD.as("LOGIN");
    /**
     * The constant COUPONS.
     */
    public static final CustomerAvailCoupons COUPONS = CustomerAvailCoupons.CUSTOMER_AVAIL_COUPONS.as("COUPONS");
    /**
     * The constant UPGRADE.
     */
    public static final CardUpgrade UPGRADE = CardUpgrade.CARD_UPGRADE.as("UPGRADE");
    /**
     * The constant CHARGE.
     */
    public static final ChargeMoney CHARGE = ChargeMoney.CHARGE_MONEY.as("CHARGE");

    /**
     * Gets uv.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the uv
     */
    public Integer getUv(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = LOGIN.CREATE_TIME.ge(startTime).and(LOGIN.CREATE_TIME.lessThan(endTime));
        return db().select(DSL.countDistinct(LOGIN.USER_ID)).from(LOGIN).where(timeCondition).fetchOneInto(Integer.class);
    }

    /**
     * Gets pv.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the pv
     */
    public Integer getPv(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = LOGIN.CREATE_TIME.ge(startTime).and(LOGIN.CREATE_TIME.lessThan(endTime));
        return db().fetchCount(LOGIN, timeCondition);
    }

    /**
     * Gets user num.新增用户数
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the user num
     */
    public Integer getUserNum(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = User.USER.CREATE_TIME.ge(startTime).and(User.USER.CREATE_TIME.lessThan(endTime));
        return db().fetchCount(User.USER, timeCondition);
    }

    /**
     * Gets user total.累积用户数
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the user total
     */
    public Integer getUserTotal(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = User.USER.CREATE_TIME.lessThan(endTime);
        return db().fetchCount(User.USER, timeCondition);
    }

    /**
     * Receive coupon user num integer.领券会员数
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the integer
     */
    public Integer receiveCouponUserNum(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = COUPONS.CREATE_TIME.ge(startTime).and(COUPONS.CREATE_TIME.lessThan(endTime));
        return db().select(DSL.countDistinct(COUPONS.USER_ID)).from(COUPONS).where(timeCondition).fetchOneInto(Integer.class);
    }

    /**
     * Upgrade user sum integer.升级会员数
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the integer
     */
    public Integer upgradeUserSum(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = UPGRADE.CREATE_TIME.ge(startTime).and(UPGRADE.CREATE_TIME.lessThan(endTime));
        return db().select(DSL.countDistinct(UPGRADE.USER_ID)).from(UPGRADE).where(timeCondition).fetchOneInto(Integer.class);
    }

    /**
     * Charge user sum integer.储值会员数
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the integer
     */
    public Integer chargeUserSum(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = CHARGE.CREATE_TIME.ge(startTime).and(CHARGE.CREATE_TIME.lessThan(endTime));
        return db().select(DSL.countDistinct(CHARGE.USER_ID)).from(CHARGE).where(timeCondition).fetchOneInto(Integer.class);
    }

    /**
     * Pay new user money big decimal.新用户成交金额(排除货到付款非发货)
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the big decimal
     */
    public BigDecimal payNewUserMoney(Timestamp startTime, Timestamp endTime) {
        BigDecimal totalMoney = BIGDECIMAL_ZERO;
        Map<Integer, BigDecimal> userMoney = payUserMoney(startTime, endTime);
        // 拿到历史成交用户集合
        Set<Integer> users = payUserCollection(DateUtil.get1970TimeStamp(), startTime);
        for (Map.Entry<Integer, BigDecimal> entry : userMoney.entrySet()) {
            if (!users.contains(entry.getKey())) {
                totalMoney = totalMoney.add(entry.getValue());
            }
        }
        // TODO 虚拟订单会员卡订单成交金额
        return totalMoney;
    }

    /**
     * Pay user money map.指定时间内每一位用户成交金额(排除货到付款非发货)
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the map
     */
    public Map<Integer, BigDecimal> payUserMoney(Timestamp startTime, Timestamp endTime) {
        return db().select(ORDER_I.USER_ID, DSL.sum(ORDER_I.MONEY_PAID).add(DSL.sum(ORDER_I.USE_ACCOUNT)).add(DSL.sum(ORDER_I.MEMBER_CARD_BALANCE)))
            .from(ORDER_I).where(STATUS_CONDITION)
            .and(ORDER_I.ORDER_SN.eq(ORDER_I.MAIN_ORDER_SN).or(ORDER_I.MAIN_ORDER_SN.eq(StringUtils.EMPTY)))
            .and(ORDER_I.CREATE_TIME.ge(startTime)).and(ORDER_I.CREATE_TIME.lessThan(endTime))
            .groupBy(ORDER_I.USER_ID)
            .orderBy(ORDER_I.CREATE_TIME)
            .fetchMap(ORDER_I.USER_ID, BigDecimal.class);
    }

    /**
     * Pay user collection set.指定时间内成交用户集合(排除货到付款非发货)
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the set
     */
    public Set<Integer> payUserCollection(Timestamp startTime, Timestamp endTime) {
        return db().select(ORDER_I.USER_ID)
            .from(ORDER_I).where(STATUS_CONDITION)
            .and(ORDER_I.ORDER_SN.eq(ORDER_I.MAIN_ORDER_SN).or(ORDER_I.MAIN_ORDER_SN.eq(StringUtils.EMPTY)))
            .and(ORDER_I.CREATE_TIME.ge(startTime)).and(ORDER_I.CREATE_TIME.lessThan(endTime))
            .orderBy(ORDER_I.CREATE_TIME)
            .fetchSet(ORDER_I.USER_ID);
    }

    /**
     * Order num int.付款订单数 (待付尾款的也算)
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the int
     */
    public int orderNum(Timestamp startTime, Timestamp endTime) {
        return db().fetchCount(ORDER_I, ORDER_SN_CONDITION.and(STATUS_CONDITION)
            .and(ORDER_I.CREATE_TIME.ge(startTime)).and(ORDER_I.CREATE_TIME.lessThan(endTime)));
    }

    /**
     * Order time con condition.
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the condition
     */
    public static final Condition orderTimeCon(Timestamp startTime, Timestamp endTime) {
        return ORDER_I.CREATE_TIME.ge(startTime).and(ORDER_I.CREATE_TIME.lessThan(endTime));
    }

    /**
     * Generate order num int.下单笔数
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the int
     */
    public int generateOrderNum(Timestamp startTime, Timestamp endTime) {
        return db().fetchCount(ORDER_I, orderTimeCon(startTime, endTime));
    }

    /**
     * Generate order user num int.下单人数(生成订单就算)
     *
     * @param startTime the start time
     * @param endTime   the end time
     * @return the int
     */
    public int generateOrderUserNum(Timestamp startTime, Timestamp endTime) {
        return db().select(DSL.countDistinct(ORDER_I.USER_ID)).from(ORDER_I).where(orderTimeCon(startTime, endTime)).fetchOneInto(Integer.class);
    }

    /**
     * Has mobile num int.有手机号用户数
     *
     * @return the int
     */
    public int hasMobileNum() {
        return db().fetchCount(User.USER, User.USER.MOBILE.isNotNull());
    }

    /**
     * Gets tag user.获得标签用户
     */
    public void getTagUser() {
    }
}
