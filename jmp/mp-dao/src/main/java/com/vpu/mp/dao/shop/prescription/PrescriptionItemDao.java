package com.vpu.mp.dao.shop.prescription;

import com.vpu.mp.common.pojo.shop.table.PrescriptionItemDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PrescriptionItemRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.PRESCRIPTION_ITEM;

/**
 * 处方明细
 *
 * @author 孔德成
 * @date 2020/7/2 14:21
 */
@Repository
public class PrescriptionItemDao extends ShopBaseDao {


    /**
     * 保存
     * @param param
     * @return
     */
    public int save(PrescriptionItemDo param) {
        PrescriptionItemRecord record = db().newRecord(PRESCRIPTION_ITEM, param);
        return record.insert();
    }

    /**
     * 批量保存
     * @param list
     * @return
     */
    public int[] batchSave(List<? extends PrescriptionItemDo> list){
        List<PrescriptionItemRecord> doList=new ArrayList<>();
        list.forEach(item->{
            PrescriptionItemRecord itemRecord = db().newRecord(PRESCRIPTION_ITEM, item);
            doList.add(itemRecord);
        });
        return db().batchInsert(doList).execute();
    }


}
