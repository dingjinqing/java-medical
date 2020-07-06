package com.vpu.mp.dao.shop.prescription;

import com.vpu.mp.common.pojo.shop.table.PrescriptionDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PrescriptionRecord;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.Tables.PRESCRIPTION;

/**
 * 处方
 *
 * @author 孔德成
 * @date 2020/7/2 14:17
 */
@Repository
public class PrescriptionDao extends ShopBaseDao {

    /**
     * 添加
     *
     * @param param
     * @return
     */
    public int save(PrescriptionDo param) {
        PrescriptionRecord record = db().newRecord(PRESCRIPTION, param);
        return record.insert();
    }

    /**
     * 添加
     * @param id
     * @return
     */
    public PrescriptionDo getById(Integer id) {
        return db().selectFrom(PRESCRIPTION).where(PRESCRIPTION.ID.eq(id)).fetchAnyInto(PrescriptionDo.class);
    }

    /**
     * 添加
     * @param id
     * @return
     */
    public <E> E  getById(Integer id,Class<? extends E> type) {
        return db().selectFrom(PRESCRIPTION).where(PRESCRIPTION.ID.eq(id)).fetchAnyInto(type);
    }


}
