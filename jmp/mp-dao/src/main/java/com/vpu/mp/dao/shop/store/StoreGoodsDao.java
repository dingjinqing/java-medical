package com.vpu.mp.dao.shop.store;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoods;
import com.vpu.mp.service.pojo.shop.store.goods.StoreGoodsUpdateTimeParam;
import org.jooq.Record6;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static com.vpu.mp.db.shop.tables.Goods.GOODS;
import static com.vpu.mp.db.shop.tables.GoodsSpecProduct.GOODS_SPEC_PRODUCT;
import static com.vpu.mp.db.shop.tables.StoreGoods.STORE_GOODS;

/**
 * @author 赵晓东
 * @description
 * @create 2020-08-20 14:49
 **/
@Repository
public class StoreGoodsDao extends ShopBaseDao {

    /**
     * 查询商品主表中需要同步至门店的信息
     * @param param 更新入参
     * @return List<StoreGoods>
     */
    public List<StoreGoods> selectMainGoods(StoreGoodsUpdateTimeParam param) {
        SelectConditionStep<Record6<Integer, Byte, Integer, String, Integer, BigDecimal>> select
            = db().select(GOODS.GOODS_ID, GOODS.IS_ON_SALE, GOODS_SPEC_PRODUCT.PRD_ID, GOODS_SPEC_PRODUCT.PRD_SN,
            GOODS_SPEC_PRODUCT.PRD_NUMBER.as("product_number"),
            GOODS_SPEC_PRODUCT.PRD_PRICE.as("product_price"))
            .from(GOODS).innerJoin(GOODS_SPEC_PRODUCT)
            .on(GOODS.GOODS_ID.eq(GOODS_SPEC_PRODUCT.GOODS_ID))
            .where(GOODS.DEL_FLAG.eq(DelFlag.NORMAL.getCode()));
        if (param.getUpdateTye() == 0) {
            select.and(GOODS_SPEC_PRODUCT.UPDATE_TIME.between(param.getUpdateBegin(), param.getUpdateEnd()));
        }
        return select.fetchInto(StoreGoods.class);
    }

    /**
     * 查询更新的商品数量
     * @param param
     * @return
     */
    public List<Integer> selectGoodsPrdIdsForUpdate(StoreGoodsUpdateTimeParam param) {
        return db().select(STORE_GOODS.PRD_ID)
            .from(STORE_GOODS)
            .where(STORE_GOODS.STORE_ID.eq(param.getStoreId()))
            .fetchInto(Integer.class);
    }

}
