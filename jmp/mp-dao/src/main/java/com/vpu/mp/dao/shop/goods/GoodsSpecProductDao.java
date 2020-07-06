package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsSpecProductDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.GOODS_SPEC_PRODUCT;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class GoodsSpecProductDao extends ShopBaseDao {

    /**
     * 商品sku新增
     * @param goodsSpecProductDos sku数据集合
     */
    public void batchInsert(List<GoodsSpecProductDo> goodsSpecProductDos){
        List<GoodsSpecProductRecord> goodsSpecProductRecords = new ArrayList<>(goodsSpecProductDos.size());

        for (GoodsSpecProductDo goodsSpecProductDo : goodsSpecProductDos) {
            GoodsSpecProductRecord goodsSpecProductRecord = new GoodsSpecProductRecord();
            FieldsUtil.assign(goodsSpecProductDo,goodsSpecProductRecord);
            goodsSpecProductRecords.add(goodsSpecProductRecord);
        }
       db().batchInsert(goodsSpecProductRecords).execute();
    }

    /**
     * 商品sku修改
     * @param goodsSpecProductDos sku数据集合
     */
    public void batchUpdate(List<GoodsSpecProductDo> goodsSpecProductDos){
        List<GoodsSpecProductRecord> goodsSpecProductRecords = new ArrayList<>(goodsSpecProductDos.size());

        for (GoodsSpecProductDo goodsSpecProductDo : goodsSpecProductDos) {
            GoodsSpecProductRecord goodsSpecProductRecord = new GoodsSpecProductRecord();
            FieldsUtil.assign(goodsSpecProductDo,goodsSpecProductRecord);
            goodsSpecProductRecords.add(goodsSpecProductRecord);
        }
        db().batchUpdate(goodsSpecProductRecords).execute();
    }

    public void deleteByGoodsId(Integer goodsId) {
        db().update(GOODS_SPEC_PRODUCT)
            .set(GOODS_SPEC_PRODUCT.PRD_SN, DSL.concat(DelFlag.DEL_ITEM_PREFIX).concat(GOODS_SPEC_PRODUCT.PRD_SN))
            .set(GOODS_SPEC_PRODUCT.PRD_CODES,DSL.concat(DelFlag.DEL_ITEM_PREFIX).concat(GOODS_SPEC_PRODUCT.PRD_CODES))
            .set(GOODS_SPEC_PRODUCT.DEL_FLAG,DelFlag.DISABLE_VALUE)
            .where(GOODS_SPEC_PRODUCT.GOODS_ID.eq(goodsId))
            .execute();
    }
}
