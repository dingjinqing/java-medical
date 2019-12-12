package com.vpu.mp.service.shop.task.overview;

import com.vpu.mp.db.shop.tables.*;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.jooq.Condition;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;

/**
 * @author liufei
 * @date 12/11/19
 * 用户相关统计信息
 */
public class UserSummaryTaskService extends ShopBaseService {
    public static final UserLoginRecord LOGIN = UserLoginRecord.USER_LOGIN_RECORD.as("LOGIN");
    public static final CustomerAvailCoupons COUPONS = CustomerAvailCoupons.CUSTOMER_AVAIL_COUPONS.as("COUPONS");
    public static final CardUpgrade UPGRADE = CardUpgrade.CARD_UPGRADE.as("UPGRADE");
    public static final ChargeMoney CHARGE = ChargeMoney.CHARGE_MONEY.as("CHARGE");

    public Integer getUv(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = LOGIN.CREATE_TIME.ge(startTime).and(LOGIN.CREATE_TIME.lessThan(endTime));
        return db().select(DSL.countDistinct(LOGIN.USER_ID)).from(LOGIN).where(timeCondition).fetchOneInto(Integer.class);
    }

    public Integer getPv(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = LOGIN.CREATE_TIME.ge(startTime).and(LOGIN.CREATE_TIME.lessThan(endTime));
        return db().fetchCount(LOGIN, timeCondition);
    }

    //  新增用户数
    public Integer getUserNum(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = User.USER.CREATE_TIME.ge(startTime).and(User.USER.CREATE_TIME.lessThan(endTime));
        return db().fetchCount(User.USER, timeCondition);
    }

    // 累积用户数
    public Integer getUserTotal(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = User.USER.CREATE_TIME.lessThan(endTime);
        return db().fetchCount(User.USER, timeCondition);
    }

    // 领券会员数
    public Integer receiveCouponUserNum(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = COUPONS.CREATE_TIME.ge(startTime).and(COUPONS.CREATE_TIME.lessThan(endTime));
        return db().select(DSL.countDistinct(COUPONS.USER_ID)).from(COUPONS).where(timeCondition).fetchOneInto(Integer.class);
    }

    // 加购用户数
    public void addCartUserNum() {
    }

    // 升级会员数
    public Integer upgradeUserSum(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = UPGRADE.CREATE_TIME.ge(startTime).and(UPGRADE.CREATE_TIME.lessThan(endTime));
        return db().select(DSL.countDistinct(UPGRADE.USER_ID)).from(UPGRADE).where(timeCondition).fetchOneInto(Integer.class);
    }

    // 储值会员数
    public Integer chargeUserSum(Timestamp startTime, Timestamp endTime) {
        Condition timeCondition = CHARGE.CREATE_TIME.ge(startTime).and(CHARGE.CREATE_TIME.lessThan(endTime));
        return db().select(DSL.countDistinct(CHARGE.USER_ID)).from(CHARGE).where(timeCondition).fetchOneInto(Integer.class);
    }

    // 成交用户数
    public void orderUserNum() {
    }

    // 总成交用户数
    public void payUserNum() {
    }

    // 新成交用户数
    public void payNewUserNum() {
    }

    // 成交金额(排除货到付款非发货)
    public BigDecimal orderUserMoney(Timestamp startTime, Timestamp endTime) {
        // todo 商品订单成交金额+会员卡订单成交金额
        return goodsOrderTurnover(startTime, endTime).add(cardOrderTurnover(startTime, endTime)).setScale(2, RoundingMode.HALF_UP);
    }

    // 商品订单成交金额
    public BigDecimal goodsOrderTurnover(Timestamp startTime, Timestamp endTime) {
        return null;
    }

    // 会员卡订单成交金额
    public BigDecimal cardOrderTurnover(Timestamp startTime, Timestamp endTime) {
        return null;
    }

    // 总成交金额(排除货到付款非发货)
    public void payUserMoney() {
    }

    // 新用户成交金额(排除货到付款非发货)
    public void payNewUserMoney() {
    }

    // 成交件数(排除货到付款非发货)
    public void orderGoodsData() {
    }

    // 付款订单数 (待付尾款的也算)
    public void orderNum() {
    }

    // 下单笔数
    public void generateOrderNum() {
    }

    // 下单人数(生成订单就算)
    public void generateOrderUserNum() {
    }

    // 有手机号用户数
    public void hasMobileNum() {
    }

    // 获得标签用户
    public void getTagUser() {
    }
}
