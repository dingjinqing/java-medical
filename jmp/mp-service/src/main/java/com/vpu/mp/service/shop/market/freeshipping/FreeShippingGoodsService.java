package com.vpu.mp.service.shop.market.freeshipping;

import com.vpu.mp.db.shop.tables.records.FreeShippingRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 满包邮商品列表业务
 * @author 孔德成
 * @date 2019/12/12 16:00
 */
@Service
public class FreeShippingGoodsService extends ShopBaseService {

    @Autowired
    private FreeShippingRuleService freeShipRuleService;
    @Autowired
    private FreeShippingService freeShipService;
    @Autowired
    private ShopCommonConfigService shopCommonConfigService;

    /**
     * 满包邮商品列表
     * @param userId 用户Id
     * @param ruleId 满包邮规则
     * @param searchText 查询条件
     */
    public void freeShipGoodsList(Integer userId,Integer ruleId,Integer searchText){
        FreeShippingRecord freeShippingById = freeShipService.getFreeShippingById(ruleId);
        /**
         * 获取售罄商品展示设置
         */
        Byte soldOutGoods = shopCommonConfigService.getSoldOutGoods();


    }
}
