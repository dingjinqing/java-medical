package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.db.shop.tables.FanliGoodsStatistics;
import com.vpu.mp.db.shop.tables.records.FanliGoodsStatisticsRecord;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 商品返利统计表
 * @author 王帅
 */
@Service
public class FanliGoodsStatisticsService extends ShopBaseService {
    FanliGoodsStatistics TABLE = FanliGoodsStatistics.FANLI_GOODS_STATISTICS;

    /**
     * 删除record，并不入库
     * @param realRebateMoney
     * @param goods
     * @param rebateNumber
     * @return
     */
    public FanliGoodsStatisticsRecord createRecord(BigDecimal realRebateMoney, OrderGoodsRecord goods, Integer rebateNumber) {
        FanliGoodsStatisticsRecord record = db().newRecord(TABLE);
        record.setPrdId(goods.getProductId());
        record.setPrdSn(goods.getProductSn());
        record.setGoodsId(goods.getGoodsId());
        record.setSaleNumber(rebateNumber);
        record.setPrdTotalFanli(realRebateMoney);
        //TODO 无需要
        record.setCatId(0);
        return record;
    }
}
