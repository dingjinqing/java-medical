package com.vpu.mp.dao.shop.medicalHistory;

import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.db.shop.tables.records.MedicalAdviceRecord;
import com.vpu.mp.db.shop.tables.records.MedicalHistoryRecord;
import com.vpu.mp.service.pojo.shop.medical.FetchMedicalAdviceVo;
import com.vpu.mp.service.pojo.shop.medicalhistory.FetchMedicalHistoryVo;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.tables.MedicalAdvice.MEDICAL_ADVICE;
import static com.vpu.mp.db.shop.tables.MedicalHistory.MEDICAL_HISTORY;


/**
 * @author 赵晓东
 * @description
 * @create 2020-07-24 09:48
 **/

@Repository
public class MedicalAdviceDao extends ShopBaseDao {

    /**
     * 新增医嘱
     * @param fetchMedicalAdviceVo 医嘱入参
     */
    public void addHitsMedicalAdvice(FetchMedicalAdviceVo fetchMedicalAdviceVo){
        MedicalAdviceRecord medicalAdviceRecord = db().newRecord(MEDICAL_ADVICE, fetchMedicalAdviceVo);
        medicalAdviceRecord.insert();
    }

    /**
     * 医嘱详情查询
     *
     * @param posCode 医嘱单号
     * @return Integer
     */
    public Integer getMedicalAdviceByCode(String posCode) {
        return db().select(MEDICAL_ADVICE.ID).from(MEDICAL_ADVICE)
            .where(MEDICAL_ADVICE.POS_CODE.eq(posCode)
                .and(MEDICAL_ADVICE.IS_DELETE.eq(DelFlag.NORMAL_VALUE))).fetchOneInto(Integer.class);
    }

    /**
     * 更新医嘱
     * @param fetchMedicalAdviceVo 处方入参
     */
    public void updateHitsMedicalAdvice(FetchMedicalAdviceVo fetchMedicalAdviceVo){
        MedicalAdviceRecord medicalAdviceRecord = db().select().from(MEDICAL_ADVICE)
            .where(MEDICAL_ADVICE.ID.eq(fetchMedicalAdviceVo.getId()))
            .fetchOneInto(MedicalAdviceRecord.class);
        FieldsUtil.assign(fetchMedicalAdviceVo, medicalAdviceRecord);
        medicalAdviceRecord.update();
        fetchMedicalAdviceVo.setId(medicalAdviceRecord.getId());
    }


}
