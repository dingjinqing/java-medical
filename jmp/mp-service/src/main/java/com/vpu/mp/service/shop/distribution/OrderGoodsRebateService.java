package com.vpu.mp.service.shop.distribution;

import com.vpu.mp.db.shop.tables.OrderGoodsRebate;
import com.vpu.mp.db.shop.tables.records.OrderGoodsRebateRecord;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.foundation.util.BigDecimalUtil;
import com.vpu.mp.service.pojo.shop.distribution.DistributionStrategyParam;
import com.vpu.mp.service.pojo.shop.order.OrderConstant;
import com.vpu.mp.service.pojo.wxapp.order.goods.OrderGoodsBo;
import com.vpu.mp.service.pojo.wxapp.order.marketing.rebate.RebateRecord;
import org.jooq.Result;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品返利详情
 */
@Service
public class OrderGoodsRebateService extends ShopBaseService {
    OrderGoodsRebate TABLE = OrderGoodsRebate.ORDER_GOODS_REBATE;

    public ArrayList<OrderGoodsRebateRecord> add(List<RebateRecord> rebateRecords, OrderGoodsBo bo, BigDecimal canRebateMoney, BigDecimal check, String orderSn) {
        ArrayList<OrderGoodsRebateRecord> result = new ArrayList<OrderGoodsRebateRecord>(rebateRecords.size());
        BigDecimal goodsTotalRebateMoney = BigDecimalUtil.BIGDECIMAL_ZERO;
        for (RebateRecord rebateRecord: rebateRecords) {
            OrderGoodsRebateRecord record = db().newRecord(TABLE);
            record.setOrderSn(orderSn);
            record.setProductId(bo.getProductId());
            record.setTotalRebateMoney(BigDecimalUtil.multiply(canRebateMoney, rebateRecord.getRatio()));
            record.setRebateMoney(BigDecimalUtil.divide(record.getTotalRebateMoney(), new BigDecimal(bo.getGoodsNumber()), RoundingMode.HALF_DOWN));
            goodsTotalRebateMoney = goodsTotalRebateMoney.add(record.getTotalRebateMoney());
            result.add(record);
        }
        //成本控制
        DistributionStrategyParam strategy = rebateRecords.get(0).getStrategy();
        if(strategy.getCostProtection() == OrderConstant.YES && BigDecimalUtil.compareTo(goodsTotalRebateMoney, check) > 0) {
            BigDecimal limitRatio = BigDecimalUtil.divide(check, goodsTotalRebateMoney, RoundingMode.HALF_DOWN);
            for (OrderGoodsRebateRecord record: result) {
                record.setTotalRebateMoney(BigDecimalUtil.multiply(record.getTotalRebateMoney(), limitRatio));
                record.setRebateMoney(BigDecimalUtil.divide(record.getTotalRebateMoney(), new BigDecimal(bo.getGoodsNumber()), RoundingMode.HALF_DOWN));
            }
        }
        db().batchInsert(result).execute();
        return result;
    }

    public Result<OrderGoodsRebateRecord> get(String orderSn) {
        return db().selectFrom(TABLE).where(TABLE.ORDER_SN.eq(orderSn)).fetch();
    }
}
