package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import com.vpu.mp.service.pojo.wxapp.activity.info.list.ReducePriceForListInfo;
import org.jooq.Condition;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.impl.DSL;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.ReducePrice.REDUCE_PRICE;
import static com.vpu.mp.db.shop.tables.ReducePriceGoods.REDUCE_PRICE_GOODS;
import static com.vpu.mp.db.shop.tables.ReducePriceProduct.REDUCE_PRICE_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
public class ReducePriceProcessorDao extends ShopBaseService {

    /**
     * 获取商品的限时降价最低价格信息（正在进行或者将会进行的限时降价信息）
     * @param goodsIds 商品id集合
     * @param date 时间
     * @return key:商品id value:{@link com.vpu.mp.service.pojo.wxapp.activity.info.list.ReducePriceForListInfo}
     */
    public  Map<Integer, ReducePriceForListInfo> getGoodsReduceListInfo(List<Integer> goodsIds, Timestamp date) {
        // 获取正在进行或未来有效的限时降价活动
        Map<Integer, List<Record2<Integer, Integer>>> goodsGroup = db().select(REDUCE_PRICE.ID, REDUCE_PRICE_GOODS.GOODS_ID).from(REDUCE_PRICE)
            .innerJoin(REDUCE_PRICE_GOODS).on(REDUCE_PRICE.ID.eq(REDUCE_PRICE_GOODS.REDUCE_PRICE_ID))
            .where(REDUCE_PRICE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(REDUCE_PRICE.STATUS.eq(GoodsConstant.USE_STATUS))
            .and(REDUCE_PRICE_GOODS.GOODS_ID.in(goodsIds))
            .and(REDUCE_PRICE.END_TIME.gt(date))
            .orderBy(REDUCE_PRICE.CREATE_TIME)
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(REDUCE_PRICE_GOODS.GOODS_ID)));

        Condition condition = DSL.noCondition();
        for (Map.Entry<Integer, List<Record2<Integer, Integer>>> entry : goodsGroup.entrySet()) {
            Integer key = entry.getKey();
            List<Record2<Integer, Integer>> value = entry.getValue();
            // 按活动时间排序
            condition = condition.or(REDUCE_PRICE_PRODUCT.GOODS_ID.eq(key).and(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID.eq(value.get(0).get(REDUCE_PRICE.ID))));
        }

        Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> reducePricesInfos = db().select(REDUCE_PRICE.ID, REDUCE_PRICE_PRODUCT.GOODS_ID, REDUCE_PRICE_PRODUCT.PRD_PRICE)
            .from(REDUCE_PRICE).innerJoin(REDUCE_PRICE_PRODUCT).on(REDUCE_PRICE.ID.eq(REDUCE_PRICE_PRODUCT.REDUCE_PRICE_ID))
            .where(condition)
            .orderBy(REDUCE_PRICE_PRODUCT.PRD_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(REDUCE_PRICE_PRODUCT.GOODS_ID)));

        Map<Integer, ReducePriceForListInfo> returnMap = new HashMap<>(reducePricesInfos.size());

        reducePricesInfos.forEach((key,value)->{
            ReducePriceForListInfo info = new ReducePriceForListInfo();
            Record3<Integer, Integer, BigDecimal> record3 = value.get(0);
            info.setActivityPrice(record3.get(REDUCE_PRICE_PRODUCT.PRD_PRICE));
            info.setActivityId(record3.get(REDUCE_PRICE.ID));
            returnMap.put(key,info);
        });

        return returnMap;
    }
}
