package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import org.springframework.stereotype.Repository;

/**
 * 商品dao
 * @author 李晓冰
 * @date 2020年07月01日
 */
@Repository
public class GoodsDao extends ShopBaseDao {
    /**
     * 商品新增
     * @param goodsDo 商品数据
     * @return 商品id
     */
    public Integer insert(GoodsDo goodsDo){
        GoodsRecord goodsRecord = new GoodsRecord();
        FieldsUtil.assign(goodsDo,goodsRecord);
        db().executeInsert(goodsRecord);
        return goodsRecord.getGoodsId();
    }

}
