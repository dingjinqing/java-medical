package com.vpu.mp.service.shop.config;

import com.vpu.mp.db.shop.tables.Payment;
import com.vpu.mp.service.pojo.shop.config.trade.OrderProcessParam;

/**
 * @Author:liufei
 * @Date:2019/7/8
 * @Description:
 */
public class TradeService extends BaseShopConfigService {

    /** 是否启用快递 */
    final public static String K_EXPRESS = "express";

    /** 是否启用自提 */
    final public static String K_FETCH = "fetch";

    /** 发货后drawback_days天，自动确认收货 */
    final public static String K_DRAWBACK_DAYS = "drawback_days";

    /** 确认收货后order_timeout_days天，订单完成 */
    final public static String K_ORDER_TIMEOUT_DAYS = "order_timeout_days";

    /**
     * 更新支付方式开关
     * @param payCode
     * @param enabled
     * @return
     */
    public int updatePayment(String payCode,Byte enabled){
        return db().update(Payment.PAYMENT)
                .set(Payment.PAYMENT.ENABLED,enabled)
                .where(Payment.PAYMENT.PAY_CODE.eq(payCode))
                .execute();
    }

    /**
     * 更新订单流程配置
     * @param orderProcessParam
     * @return
     */
    public boolean updateOrderProcess(OrderProcessParam orderProcessParam){
        try {
            this.transaction(()->{
                this.set(db(), K_EXPRESS, orderProcessParam.getExpress(), Byte.class);
                this.set(db(), K_FETCH, orderProcessParam.getOrderTimeoutDays(), String.class);
                this.set(db(), K_DRAWBACK_DAYS, orderProcessParam.getDrawbackDays(), String.class);
                this.set(db(), K_ORDER_TIMEOUT_DAYS, orderProcessParam.getOrderTimeoutDays(), String.class);

            });
        }
        catch(RuntimeException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
