package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.pojo.shop.config.ShopMsgTempConfig;
import org.springframework.stereotype.Service;

/**
 * @author 孔德成
 * @date 2020/7/24 9:54
 */
@Service
public class SmsAccountConfigService extends BaseShopConfigService{


    private static final String SHOP_SMS_ACCOUNT = "shop_sms_account";


    /**
     * 获取店铺风格配置
     *
     * @return
     */
    public String getShopSmsAccountConfig() {
        return this.getJsonObject(SHOP_SMS_ACCOUNT, String.class);
    }

    /**
     * 设置店铺风格配置
     *
     * @param  config
     * @return
     */
    public int setShopSmsAccountConfig(String config) {
        return this.setJsonObject(SHOP_SMS_ACCOUNT, config);
    }



}
