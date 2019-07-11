package com.vpu.mp.service.saas.wechat;

import com.vpu.mp.db.main.tables.MpAuthShop;
import com.vpu.mp.service.foundation.BaseService;
import com.vpu.mp.service.pojo.shop.config.trade.WxpayConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.WxpaySearchParam;
import org.jooq.Condition;

/**
 * @Author:liufei
 * @Date:2019/7/9
 * @Description:
 */
public class WechatService extends BaseService {

    /**
     * 更新微信支付配置
     * @param wxpayConfigParam
     * @return
     */
    public int udpateWxpayConfig (WxpayConfigParam wxpayConfigParam){
          return db().update(MpAuthShop.MP_AUTH_SHOP)
                .set(MpAuthShop.MP_AUTH_SHOP.PAY_MCH_ID, wxpayConfigParam.getPayMchId())
                .set(MpAuthShop.MP_AUTH_SHOP.PAY_KEY, wxpayConfigParam.getPayKey())
                .set(MpAuthShop.MP_AUTH_SHOP.PAY_CERT_CONTENT, wxpayConfigParam.getPayCertContent())
                .set(MpAuthShop.MP_AUTH_SHOP.PAY_KEY_CONTENT, wxpayConfigParam.getPayKeyContent())
                .where(MpAuthShop.MP_AUTH_SHOP.APP_ID.eq(wxpayConfigParam.getAppId()))
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

    /**
     * 查询微信支付配置
     * @return
     */
    public WxpayConfigParam getWxpayConfig (WxpaySearchParam wxpaySearchParam){
        return db().select(MpAuthShop.MP_AUTH_SHOP.APP_ID,
                MpAuthShop.MP_AUTH_SHOP.PAY_MCH_ID,
                MpAuthShop.MP_AUTH_SHOP.PAY_KEY,
                MpAuthShop.MP_AUTH_SHOP.PAY_CERT_CONTENT,
                MpAuthShop.MP_AUTH_SHOP.PAY_KEY_CONTENT)
                .from(MpAuthShop.MP_AUTH_SHOP)
                .where(MpAuthShop.MP_AUTH_SHOP.APP_ID.eq(wxpaySearchParam.getAppId()))
                .fetchInto(WxpayConfigParam.class).get(0);

    }


}
