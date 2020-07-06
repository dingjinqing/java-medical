package com.vpu.mp.dao.shop.goods;

import com.vpu.mp.common.pojo.shop.table.SpecValDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.SpecValsRecord;
import org.jooq.impl.DefaultDSLContext;
import org.springframework.stereotype.Repository;

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
        DefaultDSLContext db = db();
        for (SpecValDo specValDo : specValDos) {
            SpecValsRecord record = db.newRecord(SPEC_VALS);
            record.setGoodsId(specValDo.getGoodsId());
            record.setSpecId(specValDo.getSpecId());
            record.setSpecValName(specValDo.getSpecValName());
            record.insert();
            specValDo.setSpecValId(record.getSpecValId());
        }
    }

    /**
     * 根据商品id删除
     * @param goodsId
     */
    public void deleteByGoodsId(Integer goodsId) {
        db().deleteFrom(SPEC_VALS).where(SPEC_VALS.GOODS_ID.eq(goodsId)).execute();
    }
}
