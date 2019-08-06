package com.vpu.mp.service.shop.goods;

import org.springframework.stereotype.Service;

import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandConfig;
import com.vpu.mp.service.shop.config.BaseShopConfigService;

/**
 * @author 李晓冰
 * @date 2019年07月27日
 */
@Service
public class GoodsBrandConfigService extends BaseShopConfigService {
    private final static String K_ALL_BRAND = "show_all_brand";
    private final static String K_RECOM_BRAND_NAME = "recom_title";
    private final static String K_RECOM_BRAND_SHOW_TYPE = "show_rcommend_brand";

    /**
     * 配置商品品牌展示
     * @param config
     */
    public void setGoodsBrandConfig(GoodsBrandConfig config) {
        transaction(() -> {
            this.set(K_ALL_BRAND, config.getShowAllBrand());
            this.set(K_RECOM_BRAND_NAME, config.getRecomTitle());
            this.set(K_RECOM_BRAND_SHOW_TYPE, config.getShowRcommendBrandType());
        });
    }

    /**
     *
     * @return
     */
    public GoodsBrandConfig getGoodsBrandConfig() {
        GoodsBrandConfig brandConfig = new GoodsBrandConfig();
        brandConfig.setShowAllBrand(this.get(K_ALL_BRAND));
        brandConfig.setRecomTitle(this.get(K_RECOM_BRAND_NAME));
        brandConfig.setShowRcommendBrandType(this.get(K_RECOM_BRAND_SHOW_TYPE));
        return brandConfig;
    }
}
