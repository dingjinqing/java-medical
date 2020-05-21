package com.vpu.mp.service.shop.config;

import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;

@Service
public class ShopCommonConfigCacheService extends BaseShopConfigService{

    @Autowired
    private JedisManager jedisManager;

    public static final Integer MAX_TIME_OUT = 60*60*24;

    /**
     *  返回分词配置状态(cache)
     * @return true:开启｜false:关闭
     */
    public boolean enabledAnalyzerStatus(){
        String key = JedisKeyConstant.ANALYZER_STATUS+getShopId();
        String result = jedisManager.getValueAndSave(
            key,
            MAX_TIME_OUT,
            ()->db().select()
                .from(SHOP_CFG)
                .where(SHOP_CFG.K.eq(ShopCommonConfigService.K_ACCURATE_SEARCH))
                .fetchAny(SHOP_CFG.V));
        return result.equals("1");
    }
}
