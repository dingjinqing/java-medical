package com.vpu.mp.service.shop.config;

import com.vpu.mp.db.shop.tables.Payment;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.exception.BusinessException;
import com.vpu.mp.service.pojo.shop.config.trade.OrderProcessParam;
import com.vpu.mp.service.pojo.shop.config.trade.PaymentConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.PaymentConfigVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liufei
 * @date 2019/7/8
 */
@Slf4j
@Service
public class TradeService extends BaseShopConfigService {
    /**
     * 会员卡余额支付
     */
    final public static String K_CARD_FIRST = "card_first";
    /**
     * 余额支付
     */
    final public static String K_BALANCE_FIRST = "balance_first";
    /**
     * 积分支付
     */
    final public static String K_SCORE_FIRST = "score_first";

    /**
     * 是否启用快递
     */
    final public static String K_EXPRESS = "express";

    /**
     * 是否启用自提
     */
    final public static String K_FETCH = "fetch";

    /**
     * 发货后drawback_days天，自动确认收货
     */
    final public static String K_DRAWBACK_DAYS = "drawback_days";

    /**
     * 确认收货后order_timeout_days天，订单完成
     */
    final public static String K_ORDER_TIMEOUT_DAYS = "order_timeout_days";

    public Byte getExpress() {
        return this.get(K_EXPRESS, Byte.class, (byte) 0);
    }

    public int setExpress(Byte express) {
        assert (express == (byte) 0 || express == (byte) 1);
        return this.set(K_EXPRESS, express, Byte.class);
    }

    public Byte getFetch() {
        return this.get(K_FETCH, Byte.class, (byte) 0);
    }

    public int setFetch(Byte fetch) {
        assert (fetch == (byte) 0 || fetch == (byte) 1);
        return this.set(K_FETCH, fetch, Byte.class);
    }

    public Byte getDrawbackDays() {
        return this.get(K_DRAWBACK_DAYS, Byte.class, (byte) 0);
    }

    public int setDrawbackDays(Byte drawbackDays) {
        return drawbackDays != null ? this.set(K_DRAWBACK_DAYS, drawbackDays, Byte.class) : -1;
    }

    public Byte getOrderTimeoutDays() {
        return this.get(K_ORDER_TIMEOUT_DAYS, Byte.class, (byte) 0);
    }

    public int setOrderTimeoutDays(Byte orderTimeoutDays) {
        return orderTimeoutDays != null ? this.set(K_ORDER_TIMEOUT_DAYS, orderTimeoutDays, Byte.class) : -1;
    }

    public Byte getCardFirst() {
        return this.get(K_CARD_FIRST, Byte.class, (byte) 0);
    }

    public Byte getBalanceFirst() {
        return this.get(K_BALANCE_FIRST, Byte.class, (byte) 0);
    }

    public Byte getScoreFirst() {
        return this.get(K_SCORE_FIRST, Byte.class, (byte) 0);
    }

    public int setCardFirst(Byte cardFirst) {
        assert (cardFirst == (byte) 0 || cardFirst == (byte) 1);
        return this.set(K_CARD_FIRST, cardFirst, Byte.class);
    }

    public int setBalanceFirst(Byte balanceFirst) {
        assert (balanceFirst == (byte) 0 || balanceFirst == (byte) 1);
        return this.set(K_BALANCE_FIRST, balanceFirst, Byte.class);
    }

    public int setScoreFirst(Byte scoreFirst) {
        assert (scoreFirst == (byte) 0 || scoreFirst == (byte) 1);
        return this.set(K_SCORE_FIRST, scoreFirst, Byte.class);
    }

    /**
     * 更新支付方式开关
     *
     * @param payCode 支付方式码
     * @param enabled 支付开关状态
     */
    public void updatePayment(String payCode, Byte enabled) {
        db().update(Payment.PAYMENT)
            .set(Payment.PAYMENT.ENABLED, enabled)
            .where(Payment.PAYMENT.PAY_CODE.eq(payCode))
            .execute();
    }

    /**
     * 更新默认支付配置
     */
    public void updateDefaultPayConf(PaymentConfigParam param) {
        try {
            this.transaction(() -> {
                if(param.getCardFirst()!=null){
                    this.setCardFirst(param.getCardFirst());
                }
                if(param.getBalanceFirst()!=null){
                    this.setBalanceFirst(param.getBalanceFirst());
                }
                if(param.getScoreFirst()!=null){
                    this.setScoreFirst(param.getScoreFirst());
                }
            });
        } catch (RuntimeException e) {
            log.error("更新默认支付配置事务执行失败！错误：{}", e.getMessage());
            throw new BusinessException(JsonResultCode.CODE_CONFIG_UPDATE_FAILED);
        }
    }

    /**
     * 查询支付方式开关
     */
    public List<PaymentConfigVo> getPaymentEnabled() {
        return db().select(Payment.PAYMENT.PAY_CODE, Payment.PAYMENT.PAY_NAME, Payment.PAYMENT.ENABLED)
            .from(Payment.PAYMENT).fetchInto(PaymentConfigVo.class);
    }

    /**
     * 查询默认支付配置
     */
    public Map<String, Byte> getDefaultPayConf() {
        Byte cardFirst = this.getCardFirst();
        Byte balanceFirst = this.getBalanceFirst();
        Byte scoreFirst = this.getScoreFirst();
        return new HashMap<String, Byte>(3) {
            {
                put(K_CARD_FIRST, cardFirst);
                put(K_BALANCE_FIRST, balanceFirst);
                put(K_SCORE_FIRST, scoreFirst);
            }
        };
    }

    /**
     * 更新订单流程配置
     *
     * @param orderProcessParam 订单流程配置项信息
     */
    public boolean updateOrderProcess(OrderProcessParam orderProcessParam) {
        try {
            this.transaction(() -> {
                this.setExpress(orderProcessParam.getExpress());
                this.setFetch(orderProcessParam.getFetch());
                this.setDrawbackDays(orderProcessParam.getDrawbackDays());
                this.setOrderTimeoutDays(orderProcessParam.getOrderTimeoutDays());

            });
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 查询订单流程配置
     */
    public OrderProcessParam getOrderProcessConfig() {
        OrderProcessParam orderProcessParam = new OrderProcessParam();
        orderProcessParam.setDrawbackDays(this.getDrawbackDays());
        orderProcessParam.setExpress(this.getExpress());
        orderProcessParam.setExpress(this.getFetch());
        orderProcessParam.setOrderTimeoutDays(this.getOrderTimeoutDays());
        return orderProcessParam;
    }
}
