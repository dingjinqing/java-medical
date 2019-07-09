package com.vpu.mp.service.shop.config;

import com.vpu.mp.db.shop.tables.Payment;
import com.vpu.mp.db.shop.tables.ShopCfg;
import com.vpu.mp.service.foundation.BaseService;
import org.jooq.*;
import org.jooq.impl.DSL;

import java.util.List;

/**
 * @Author:liufei
 * @Date:2019/7/8
 * @Description:
 */
public class TradeService extends BaseService {

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
     * @param shopCfgList
     * @return
     */
    public int updateOrderProcess(List<com.vpu.mp.service.pojo.shop.config.ShopCfg> shopCfgList){
        int[] result = {-1};
        db().transaction(configuration -> {
            DSLContext db = DSL.using(configuration);
            shopCfgList.forEach((e)-> result[0] = db.update(ShopCfg.SHOP_CFG).set(ShopCfg.SHOP_CFG.V,e.getV()).where(ShopCfg.SHOP_CFG.K.eq(e.getK())).execute());
        });
        return result[0];
    }
}
