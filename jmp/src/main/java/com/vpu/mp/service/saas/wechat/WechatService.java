package com.vpu.mp.service.saas.wechat;

import com.vpu.mp.db.main.tables.MpAuthShop;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.config.trade.WxpayConfigIn;
import org.jooq.Condition;

/**
 * @Author:liufei
 * @Date:2019/7/9
 * @Description:
 */
public class WechatService extends BaseService {

    /**
     * 更新微信支付配置
     * @param wxpayConfigIn
     * @return
     */
    public int udpateWxpayConfig (WxpayConfigIn wxpayConfigIn){
          return db().update(MpAuthShop.MP_AUTH_SHOP)
                .set(MpAuthShop.MP_AUTH_SHOP.PAY_MCH_ID,wxpayConfigIn.getPayMchId())
                .set(MpAuthShop.MP_AUTH_SHOP.PAY_KEY,wxpayConfigIn.getPayKey())
                .set(MpAuthShop.MP_AUTH_SHOP.PAY_CERT_CONTENT,wxpayConfigIn.getPayCertContent())
                .set(MpAuthShop.MP_AUTH_SHOP.PAY_KEY_CONTENT,wxpayConfigIn.getPayKeyContent())
                .where(MpAuthShop.MP_AUTH_SHOP.APP_ID.eq(wxpayConfigIn.getAppId()))
                .execute();
    }

    /**
     * 根据appid检测MpAuthShop表中数据存在性
     * @param appId
     * @return true存在，false不存在
     */
    public boolean checkAuthShopExist(String appId){
        Condition conditionAuthShop = MpAuthShop.MP_AUTH_SHOP.APP_ID.eq(appId);
        return db().fetchCount(MpAuthShop.MP_AUTH_SHOP,conditionAuthShop) > 0;
    }
}
