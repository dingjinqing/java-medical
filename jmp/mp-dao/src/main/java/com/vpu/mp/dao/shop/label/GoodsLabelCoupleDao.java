package com.vpu.mp.dao.shop.label;

import com.vpu.mp.common.pojo.shop.table.GoodsLabelCoupleDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.GoodsLabelCoupleRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */

@Repository
public class GoodsLabelCoupleDao extends ShopBaseDao {

    public void insert(List<GoodsLabelCoupleDo> goodsLabelCoupleDos){
        List<GoodsLabelCoupleRecord> goodsLabelCoupleRecords = new ArrayList<>(goodsLabelCoupleDos.size());

        for (GoodsLabelCoupleDo goodsLabelCoupleDo : goodsLabelCoupleDos) {
            GoodsLabelCoupleRecord record = new GoodsLabelCoupleRecord();
            record.setLabelId(goodsLabelCoupleDo.getLabelId());
            record.setGtaId(goodsLabelCoupleDo.getGtaId());
            record.setType(goodsLabelCoupleDo.getType());
            goodsLabelCoupleRecords.add(record);
        }

        db().batchInsert(goodsLabelCoupleRecords).execute();
    }
}
