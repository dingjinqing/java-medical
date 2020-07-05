package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.pojo.shop.table.SpecValDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.SpecValsRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.SPEC_VALS;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Repository
public class SpecValDao extends ShopBaseDao {

    /**
     * 规格值新增
     * @param specValDos 规格名集合
     * @return 规格名id集合
     */
    public void batchInsert(List<SpecValDo> specValDos) {
        List<SpecValsRecord> specValRecords = new ArrayList<>();
        for (SpecValDo specValDo : specValDos) {
            SpecValsRecord record = new SpecValsRecord();
            record.setGoodsId(specValDo.getGoodsId());
            record.setSpecId(specValDo.getSpecId());
            specValRecords.add(record);
        }

        db().batchInsert(specValRecords).execute();

        for (int i = 0; i < specValRecords.size(); i++) {
            specValRecords.get(i).refresh(SPEC_VALS.SPEC_VAL_ID);
            specValDos.get(i).setSpecValId(specValRecords.get(i).getSpecValId());
        }
    }

}
