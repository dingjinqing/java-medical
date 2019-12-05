package com.vpu.mp.service.shop.activity.dao;

import com.vpu.mp.db.shop.tables.records.FirstSpecialProductRecord;
import com.vpu.mp.service.foundation.data.DelFlag;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.goods.GoodsConstant;

import org.jooq.Condition;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Record4;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.db.shop.tables.FirstSpecial.FIRST_SPECIAL;
import static com.vpu.mp.db.shop.tables.FirstSpecialGoods.FIRST_SPECIAL_GOODS;
import static com.vpu.mp.db.shop.tables.FirstSpecialProduct.FIRST_SPECIAL_PRODUCT;

/**
 * @author 李晓冰
 * @date 2019年11月01日
 */
@Service
public class FirstSpecialProcessorDao extends ShopBaseService {

    public static byte FOREVER_YES = 1;
    public static byte FOREVER_NO = 0;

    /**
     * 获取集合内商品的首单特惠信息
     * @param goodsIds 商品id集合
     * @param date 日期
     * @return key: 商品id，value: Result<Record3<Integer, Integer, BigDecimal>>
     */
   public  Map<Integer,  Result<Record3<Integer, Integer, BigDecimal>>> getGoodsFirstSpecialForListInfo(List<Integer> goodsIds, Timestamp date){
       Map<Integer, Result<Record2<Integer, Integer>>> firstSpecials = db().select(FIRST_SPECIAL.ID, FIRST_SPECIAL_GOODS.GOODS_ID).from(FIRST_SPECIAL).innerJoin(FIRST_SPECIAL_GOODS).on(FIRST_SPECIAL.ID.eq(FIRST_SPECIAL_GOODS.FIRST_SPECIAL_ID))
           .where(FIRST_SPECIAL.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
           .and(FIRST_SPECIAL.STATUS.eq(GoodsConstant.USE_STATUS))
           .and(FIRST_SPECIAL_GOODS.GOODS_ID.in(goodsIds))
           .and(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_YES)
               .or(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_NO).and(FIRST_SPECIAL.START_TIME.lt(date).and(FIRST_SPECIAL.END_TIME.gt(date)))))
           .orderBy(FIRST_SPECIAL.FIRST.desc())
           .fetch().intoGroups(FIRST_SPECIAL_GOODS.GOODS_ID);

       Condition condition = DSL.noCondition();
       for (Map.Entry<Integer,Result<Record2<Integer, Integer>>> entry : firstSpecials.entrySet()) {
           Record2<Integer, Integer> value = entry.getValue().get(0);
           condition = condition.or(FIRST_SPECIAL_PRODUCT.FIRST_SPECIAL_ID.eq(value.get(FIRST_SPECIAL.ID)).and(FIRST_SPECIAL_PRODUCT.GOODS_ID.eq(value.get(FIRST_SPECIAL_GOODS.GOODS_ID))));
       }

       return db().select(FIRST_SPECIAL_PRODUCT.ID, FIRST_SPECIAL_PRODUCT.GOODS_ID, FIRST_SPECIAL_PRODUCT.PRD_PRICE)
           .from(FIRST_SPECIAL_PRODUCT)
           .where(condition)
           .fetch().intoGroups(FIRST_SPECIAL_PRODUCT.GOODS_ID);
   }

    /**
     * 获取首单特惠--规格活动信息
     * @param productIdList 规格Id
     * @return  firstSpecialsPrdIdList
     */
    public Result<?extends Record> getGoodsFirstSpecialPrdId(List<Integer> productIdList, Timestamp date) {
        return db().select(FIRST_SPECIAL_PRODUCT.ID,FIRST_SPECIAL_PRODUCT.PRD_ID, FIRST_SPECIAL_PRODUCT.PRD_PRICE, FIRST_SPECIAL.LIMIT_AMOUNT, FIRST_SPECIAL.LIMIT_FLAG)
                .from(FIRST_SPECIAL_PRODUCT)
                .leftJoin(FIRST_SPECIAL).on(FIRST_SPECIAL.ID.eq(FIRST_SPECIAL_PRODUCT.FIRST_SPECIAL_ID))
                .where(FIRST_SPECIAL.DEL_FLAG.eq(DelFlag.NORMAL.getCode()))
                .and(FIRST_SPECIAL.STATUS.eq(GoodsConstant.USE_STATUS))
                .and(FIRST_SPECIAL_PRODUCT.PRD_ID.in(productIdList))
                .and(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_YES)
                        .or(FIRST_SPECIAL.IS_FOREVER.eq(FOREVER_NO).and(FIRST_SPECIAL.START_TIME.lt(date)
                                .and(FIRST_SPECIAL.END_TIME.gt(date)))))
                .orderBy(FIRST_SPECIAL.FIRST.desc())
                .fetch();
    }

}
