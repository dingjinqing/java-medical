package com.vpu.mp.service.shop.market.freeshipping;

import com.vpu.mp.db.shop.tables.records.FreeShippingRecord;
import com.vpu.mp.db.shop.tables.records.FreeShippingRuleRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.shop.config.ShopCommonConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.vpu.mp.service.foundation.data.BaseConstant.GOODS_AREA_TYPE_SECTION;

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
     * @param ruleId 满包邮规则id
     * @param searchText 查询条件
     */
    public void freeShipGoodsList(Integer userId,Integer ruleId,Integer searchText){
        // 获取活动规则
        FreeShippingRecord shipRecord = freeShipService.getFreeShippingByRuleId(ruleId);
        //包邮规则
        FreeShippingRuleRecord freeShippingRuleRecord =freeShipRuleService.getRuleDetailByRule(ruleId);
        //获取售罄商品展示设置
        Byte soldOutGoods = shopCommonConfigService.getSoldOutGoods();
        //活动商品范围
        if (shipRecord.getType().equals(GOODS_AREA_TYPE_SECTION)){

        }




    }
}
