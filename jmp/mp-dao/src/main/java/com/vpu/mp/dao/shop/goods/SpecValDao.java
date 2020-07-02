package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.pojo.shop.goods.dao.SpecValDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.SpecValsRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class SpecValDao extends ShopBaseDao {

    /**
     * 规格名新增
     * @param specValDos 规格名集合
     * @return 规格名id集合
     */
    public List<Integer> batchInsert(List<SpecValDo> specValDos) {
        List<SpecValsRecord> specValRecords = new ArrayList<>();

        for (SpecValDo specValDo : specValDos) {
            SpecValsRecord record = new SpecValsRecord();
            record.setGoodsId(specValDo.getGoodsId());
            record.setSpecId(specValDo.getSpecId());
            specValRecords.add(record);
        }

        db().batchInsert(specValRecords).execute();

        List<Integer> specValIds = new ArrayList<>(specValDos.size());
        for (int i = 0; i < specValRecords.size(); i++) {
            specValIds.add(specValRecords.get(i).getSpecValId());
        }
        return specValIds;
    }

}
