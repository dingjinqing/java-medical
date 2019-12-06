package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;
import org.jooq.Condition;
import org.jooq.Record3;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.vpu.mp.db.shop.tables.Presale.PRESALE;
import static com.vpu.mp.db.shop.tables.PresaleProduct.PRESALE_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class PreSaleProcessorDao extends ShopBaseService {

    /**
     * 获取商品集合内的预售信息
     * @param goodsIds 商品id集合
     * @param date 日期
     * @return key:商品id，value:List<Record3<Integer, Integer, BigDecimal>> PRESALE.ID, PRESALE.GOODS_ID, PRESALE_PRODUCT.PRESALE_PRICE
     */
    public Map<Integer, List<Record3<Integer, Integer, BigDecimal>>> getGoodsPreSaleListInfo(List<Integer> goodsIds, Timestamp date) {
        // 一阶段或二阶段付定金时间限制
        // 付定金：时间限制在第一阶段或第二阶段内 ，全款：时间限制在活动指定的时间内（和第一阶段使用相同字段）
        Condition condition = (PRESALE.PRE_START_TIME.lt(date).and(PRESALE.PRE_END_TIME.gt(date))).or(PRESALE.PRE_START_TIME_2.gt(date).and(PRESALE.PRE_END_TIME_2.lt(date)));

        return db().select(PRESALE.ID, PRESALE.GOODS_ID, PRESALE_PRODUCT.PRESALE_PRICE)
            .from(PRESALE).innerJoin(PRESALE_PRODUCT).on(PRESALE.ID.eq(PRESALE_PRODUCT.PRESALE_ID))
            .where(PRESALE.GOODS_ID.in(goodsIds))
            .and(PRESALE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
            .and(PRESALE.STATUS.eq(BaseConstant.ACTIVITY_STATUS_NORMAL))
            .and(condition)
            .orderBy(PRESALE_PRODUCT.PRESALE_PRICE.asc())
            .fetch().stream().collect(Collectors.groupingBy(x -> x.get(PRESALE.GOODS_ID)));
    }

//    /**
//        ps:详情处会用到，勿删
//     *  获取集合内商品的定金膨胀信息
//     * @param goodsIds
//     * @param date
//     * @return
//     */
//    @Deprecated
//    public Map<Integer, PreSaleActivityVo> getGoodsPreSaleInfo(List<Integer> goodsIds,Timestamp date) {
//        // 一阶段或二阶段付定金时间限制
//        // 付定金：时间限制在第一阶段或第二阶段内 ，全款：时间限制在活动指定的时间内（和第一阶段使用相同字段）
//        Condition condition = (PRESALE.PRE_START_TIME.lt(date).and(PRESALE.PRE_END_TIME.gt(date))).or(PRESALE.PRE_START_TIME_2.gt(date).and(PRESALE.PRE_END_TIME_2.lt(date)));
//
//        Map<Integer, List<Record13<Integer, Integer, Byte, Byte, Byte, Timestamp, Integer, Byte, Timestamp, Timestamp, Timestamp, Timestamp, BigDecimal>>> preSaleInfos =
//            db().select(PRESALE.ID, PRESALE.GOODS_ID, PRESALE.PRESALE_TYPE, PRESALE.RETURN_TYPE, PRESALE.DELIVER_TYPE, PRESALE.DELIVER_TIME, PRESALE.DELIVER_DAYS,
//                PRESALE.PRE_PAY_STEP, PRESALE.PRE_START_TIME, PRESALE.PRE_END_TIME, PRESALE.PRE_START_TIME_2, PRESALE.PRE_END_TIME_2,
//                PRESALE_PRODUCT.PRESALE_PRICE).from(PRESALE).innerJoin(PRESALE_PRODUCT).on(PRESALE.ID.eq(PRESALE_PRODUCT.PRESALE_ID))
//                .where(PRESALE.GOODS_ID.in(goodsIds))
//                .and(PRESALE.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
//                .and(PRESALE.STATUS.eq(STATUS_NORMAL))
//                .and(condition)
//                .orderBy(PRESALE_PRODUCT.PRESALE_PRICE.asc())
//                .fetch().stream().collect(Collectors.groupingBy(x -> x.get(PRESALE.GOODS_ID)));
//
//        Map<Integer, PreSaleActivityVo> returnMap=new HashMap<>();
//
//        preSaleInfos.forEach((key,value)->{
//            Record13<Integer, Integer, Byte, Byte, Byte, Timestamp, Integer, Byte, Timestamp, Timestamp, Timestamp, Timestamp, BigDecimal> minPreSale = value.get(0);
//            Record13<Integer, Integer, Byte, Byte, Byte, Timestamp, Integer, Byte, Timestamp, Timestamp, Timestamp, Timestamp, BigDecimal> maxPreSale = value.get(value.size() - 1);
//
//            PreSaleActivityVo vo =new PreSaleActivityVo();
//            vo.setActivityId(minPreSale.get(PRESALE.ID));
//            vo.setActivityMinPrice(minPreSale.get(PRESALE_PRODUCT.PRESALE_PRICE));
//            vo.setActivityMaxPrice(maxPreSale.get(PRESALE_PRODUCT.PRESALE_PRICE));
//            vo.setReturnType(minPreSale.get(PRESALE.RETURN_TYPE));
//            vo.setDeliverType(minPreSale.get(PRESALE.DELIVER_TYPE));
//            // 设置快递寄出时间
//            if (DELIVER_TYPE_TIME.equals(vo.getDeliverType())) {
//                vo.setDeliverTime(minPreSale.get(PRESALE.DELIVER_TIME));
//            } else {
//                vo.setDeliverDays(minPreSale.get(PRESALE.DELIVER_DAYS));
//            }
//            // 设置结束时间（所有数据已被限制为处于活动状态）
//            if (date.compareTo(minPreSale.get(PRESALE.PRE_END_TIME)) < 0) {
//                vo.setCurrentActivityEndDate(minPreSale.get(PRESALE.PRE_END_TIME));
//            } else {
//                vo.setCurrentActivityEndDate(minPreSale.get(PRESALE.PRE_END_TIME_2));
//            }
//            returnMap.put(key,vo);
//        });
//        return returnMap;
//    }

}
