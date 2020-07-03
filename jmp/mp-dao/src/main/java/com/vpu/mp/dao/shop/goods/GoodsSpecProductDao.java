package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.GoodsSpecProductDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import org.springframework.stereotype.Repository;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class GoodsSpecProductDao extends ShopBaseDao {

    /**
     * 商品sku新增
     * @param goodsSpecProductDo sku数据
     * @return sku id
     */
    public Integer insert(GoodsSpecProductDo goodsSpecProductDo){
        GoodsSpecProductRecord goodsSpecProductRecord = new GoodsSpecProductRecord();
        FieldsUtil.assign(goodsSpecProductRecord,goodsSpecProductDo);
        db().executeInsert(goodsSpecProductRecord);
        return goodsSpecProductRecord.getPrdId();
    }
}
