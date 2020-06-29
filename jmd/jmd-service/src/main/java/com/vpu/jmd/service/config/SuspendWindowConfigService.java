package com.vpu.jmd.service.config;

import com.vpu.mp.service.foundation.jedis.JedisKeyConstant;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.util.Util;
import com.vpu.mp.service.pojo.shop.config.SuspendWindowConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import static com.vpu.mp.db.shop.tables.ShopCfg.SHOP_CFG;

/**
 * 悬浮窗配置
 *
 * @author: 王兵兵
 * @create: 2020-05-08 17:18
 **/
@Service
public class SuspendWindowConfigService extends BaseShopConfigService {
    @Autowired
    private JedisManager jedisManager;

    /**
     * 悬浮窗配置草稿
     * value是json格式
     */
    final public static String K_SUSPEND_CFG_DRAFT = "suspend_cfg_draft";

    /**
     * 悬浮窗配置
     * value是json格式
     */
    final public static String K_SUSPEND_CFG = "suspend_cfg";

    /**
     * @return
     */
    public SuspendWindowConfig getSuspendCfgDraft() {
        return this.getJsonObject(K_SUSPEND_CFG_DRAFT, SuspendWindowConfig.class, null);
    }

    /**
     * @param
     * @return
     */
    public int setSuspendCfgDraft(SuspendWindowConfig suspendWindowConfig) {
        Assert.isTrue(suspendWindowConfig != null, "Value must not null");
        return this.setJsonObject(K_SUSPEND_CFG_DRAFT, suspendWindowConfig);
    }

    /**
     * @return
     */
    public SuspendWindowConfig getSuspendCfg() {
        String key = JedisKeyConstant.CONFIG_SUSPEND_WINDOW + getShopId();
        String result = jedisManager.getValueAndSave(
            key,
            ShopCommonConfigCacheService.MAX_TIME_OUT,
            () -> db().select()
                .from(SHOP_CFG)
                .where(SHOP_CFG.K.eq(K_SUSPEND_CFG_DRAFT))
                .fetchAny(SHOP_CFG.V));
        return Util.parseJson(result, SuspendWindowConfig.class);
    }

    /**
     * @param
     * @return
     */
    public int setSuspendCfg(SuspendWindowConfig suspendWindowConfig) {
        Assert.isTrue(suspendWindowConfig != null, "Value must not null");
        String key = JedisKeyConstant.CONFIG_SUSPEND_WINDOW + getShopId();
        //清除缓存
        jedisManager.delete(key);
        return this.setJsonObject(K_SUSPEND_CFG, suspendWindowConfig);
    }

}
