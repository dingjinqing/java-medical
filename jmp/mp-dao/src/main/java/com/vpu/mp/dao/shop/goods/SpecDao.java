package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.pojo.shop.table.SpecDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.SpecRecord;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.SPEC;

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
    public void batchInsert(List<SpecDo> specDos) {
        DefaultDSLContext db = db();
        for (SpecDo specDo : specDos) {
            SpecRecord record = db.newRecord(SPEC);
            record.setGoodsId(specDo.getGoodsId());
            record.setSpecName(specDo.getSpecName());
            record.insert();
            specDo.setSpecId(record.getSpecId());
        }
    }
}
