package com.vpu.mp.dao.shop.prescription;

import com.vpu.mp.common.foundation.data.BaseConstant;
import com.vpu.mp.common.pojo.shop.prescription.PrescriptionItemInfoVo;
import com.vpu.mp.common.pojo.shop.table.PrescriptionItemDo;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PrescriptionItemRecord;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.db.shop.Tables.GOODS_MEDICAL_INFO;
import static com.vpu.mp.db.shop.Tables.PRESCRIPTION;
import static com.vpu.mp.db.shop.Tables.PRESCRIPTION_ITEM;
import static com.vpu.mp.db.shop.tables.Goods.GOODS;

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

    /**
     * 处方明细
     * @param prescriptionNo
     * @return
     */
    public List<PrescriptionItemInfoVo> listByPrescriptionNo(String prescriptionNo) {
        return db().select().from(PRESCRIPTION_ITEM)
                .where(PRESCRIPTION_ITEM.PRESCRIPTION_NO.eq(prescriptionNo))
                .fetchInto(PrescriptionItemInfoVo.class);
    }

    /**
     * 获取对应处方集合下的商品id
     * @return
     */
    public List<Integer> getPrescriptionGoodsIdsByPrescriptionNos(List<String> prescriptionNos) {
        return db().selectDistinct(GOODS_MEDICAL_INFO.GOODS_ID).from(PRESCRIPTION_ITEM)
            .leftJoin(GOODS_MEDICAL_INFO).on(GOODS_MEDICAL_INFO.GOODS_COMMON_NAME.eq(PRESCRIPTION_ITEM.GOODS_COMMON_NAME))
            .where(PRESCRIPTION_ITEM.PRESCRIPTION_NO.in(prescriptionNos).and(GOODS_MEDICAL_INFO.GOODS_ID.gt(0))).fetchInto(Integer.class);
    }
}
