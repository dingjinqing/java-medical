package com.vpu.mp.dao.shop.img;

import com.vpu.mp.common.pojo.shop.table.GoodsImgDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsImgRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月06日
 */
@Repository
public class GoodsImgDao extends ShopBaseDao {

    /**
     * 批量插入
     * @param goodsImgDos
     */
    public void batchInsert(List<GoodsImgDo> goodsImgDos) {
        List<GoodsImgRecord> goodsImgRecords = new ArrayList<>(goodsImgDos.size());

        for (GoodsImgDo goodsImgDo : goodsImgDos) {
            GoodsImgRecord record = new GoodsImgRecord();
            record.setGoodsId(goodsImgDo.getGoodsId());
            record.setImgUrl(goodsImgDo.getImgUrl());
            goodsImgRecords.add(record);
        }

        db().batchInsert(goodsImgRecords).execute();
    }
}
