package com.vpu.mp.dao.shop.order;

import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.OrderMedicalHistoryRecord;
import com.vpu.mp.service.pojo.wxapp.order.medical.OrderMedicalHistoryBo;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.tables.OrderMedicalHistory.ORDER_MEDICAL_HISTORY;

/**
 * 订单历史诊断
 * @author 孔德成
 * @date 2020/7/28 14:43
 */
@Repository
public class OrderMedicalHistoryDao extends ShopBaseDao {


    /**
     * 保存
     * @param patientDiagnose
     * @return
     */
    public int save(OrderMedicalHistoryBo patientDiagnose) {
        OrderMedicalHistoryRecord orderMedicalHistoryRecord = db().newRecord(ORDER_MEDICAL_HISTORY, patientDiagnose);
       return orderMedicalHistoryRecord.insert();
    }
}
