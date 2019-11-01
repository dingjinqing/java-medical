package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.PreSaleForListInfo;
import org.jooq.Condition;
import org.jooq.Record3;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Presale.PRESALE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
public class PreSaleProcessorDao extends ShopBaseService {

    /**
     * 获取商品集合内的预售信息
     * @param goodsIds 商品id集合
     * @param date 日期
     * @return key:商品id，value:{@link com.vpu.mp.service.pojo.wxapp.activity.info.list.PreSaleForListInfo}
     */
    public Map<Integer, PreSaleForListInfo> getGoodsPreSaleListInfo(List<Integer> goodsIds, Timestamp date) {
        // 一阶段或二阶段付定金时间限制
        // 付定金：时间限制在第一阶段或第二阶段内 ，全款：时间限制在活动指定的时间内（和第一阶段使用相同字段）
        Condition condition = (PRESALE.PRE_START_TIME.lt(date).and(PRESALE.PRE_END_TIME.gt(date))).or(PRESALE.PRE_START_TIME_2.gt(date).and(PRESALE.PRE_END_TIME_2.lt(date)));

        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> preSaleInfos = db().select(PRESALE.ID, PRESALE.GOODS_ID, PRESALE_PRODUCT.PRESALE_PRICE)
            .from(PRESALE).innerJoin(PRESALE_PRODUCT).on(PRESALE.ID.eq(PRESALE_PRODUCT.PRESALE_ID))
            .where(PRESALE.GOODS_ID.in(goodsIds))
            .and(PRESALE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(PRESALE.STATUS.eq(GoodsConstant.USE_STATUS))
            .and(condition)
            .orderBy(PRESALE_PRODUCT.PRESALE_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(PRESALE.GOODS_ID)));

        Map<Integer, PreSaleForListInfo> returnMap=new HashMap<>();

        preSaleInfos.forEach((key,value)->{
            PreSaleForListInfo info =new PreSaleForListInfo();
            Record3<Integer, Integer, BigDecimal> record3 = value.get(0);
            info.setActivityId(record3.get(PRESALE.ID));
            info.setActivityPrice(record3.get(PRESALE_PRODUCT.PRESALE_PRICE));
            returnMap.put(key,info);
        });
        return returnMap;
    }
}
