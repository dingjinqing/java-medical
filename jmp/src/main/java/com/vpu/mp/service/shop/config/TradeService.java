package com.vpu.mp.service.shop.config;

import com.vpu.mp.db.shop.tables.Payment;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.config.trade.WxpayConfigIn;

/**
 * @Author:liufei
 * @Date:2019/7/8
 * @Description:
 */
public class TradeService extends BaseService {

    public int updatePayment(String payCode,Byte enabled){
        return db().update(Payment.PAYMENT)
                .set(Payment.PAYMENT.ENABLED,enabled)
                .where(Payment.PAYMENT.PAY_CODE.eq(payCode))
                .execute();
    }

}
