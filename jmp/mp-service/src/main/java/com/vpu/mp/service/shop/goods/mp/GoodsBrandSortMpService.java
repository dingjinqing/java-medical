package com.vpu.mp.service.shop.goods.mp;

import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.brand.GoodsBrandConfig;
import com.vpu.mp.service.shop.config.ConfigService;
import com.vpu.mp.service.shop.goods.GoodsSortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李晓冰
 * @date 2019年10月17日
 * 处理小程商家分类和品牌相关内用
 */
@Service
public class GoodsBrandSortMpService extends ShopBaseService{
    @Autowired
    ConfigService configService;
    @Autowired
    GoodsSortService goodsSortService;

    public void goodsSortPageInit() {
        GoodsBrandConfig goodsBrandConfig = configService.goodsBrandConfigService.getGoodsBrandConfig();

    }

}
