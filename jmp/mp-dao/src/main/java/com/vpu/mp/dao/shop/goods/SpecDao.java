package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.pojo.shop.table.SpecDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.SpecRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class SpecDao extends ShopBaseDao {

    /**
     * 规格名新增
     * @param specDos 规格名集合
     * @return 规格名id集合
     */
    public List<Integer> batchInsert(List<SpecDo> specDos) {
        List<SpecRecord> specRecords = new ArrayList<>();
        for (SpecDo specDo : specDos) {
            SpecRecord record = new SpecRecord();
            record.setGoodsId(specDo.getGoodsId());
            record.setSpecName(specDo.getSpecName());
            specRecords.add(record);
        }
        db().batchInsert(specRecords).execute();
        List<Integer> specIds = new ArrayList<>(specDos.size());
        for (int i = 0; i < specRecords.size(); i++) {
            specIds.add(specRecords.get(i).getSpecId());
        }
        return specIds;
    } 
}
