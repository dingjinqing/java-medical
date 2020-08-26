package com.vpu.mp.dao.shop.rebate;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.PrescriptionRebateRecord;
import com.vpu.mp.service.pojo.shop.rebate.PrescriptionRebateParam;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.tables.PrescriptionRebate.PRESCRIPTION_REBATE;

/**
 * @author yangpengcheng
 * @date 2020/8/26
 **/
@Repository
public class PrescriptionRebateDao extends ShopBaseDao {

    /**
     * 处方返利入库
     * @param param
     */
    public void addPrescriptionRebate(PrescriptionRebateParam param){
        PrescriptionRebateRecord record=db().newRecord(PRESCRIPTION_REBATE);
        FieldsUtil.assign(param,record);
        record.insert();
    }

    /**
     * 更改返利状态
     * @param prescriptionCode
     * @param status
     */
    public void updateStatus(String prescriptionCode,Byte status){
        db().update(PRESCRIPTION_REBATE).set(PRESCRIPTION_REBATE.STATUS,status).where(PRESCRIPTION_REBATE.PRESCRIPTION_CODE.eq(prescriptionCode))
            .execute();

    }
}
