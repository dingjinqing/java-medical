package com.vpu.mp.dao.shop.medicalHistory;

import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.db.shop.tables.records.MedicalHistoryRecord;
import com.vpu.mp.db.shop.tables.records.PrescriptionRecord;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.pojo.shop.medicalHistory.*;
import com.vpu.mp.common.foundation.data.DelFlag;
import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionVo;
import org.jooq.Record;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Repository;

import static com.vpu.mp.db.shop.Tables.PRESCRIPTION;
import static com.vpu.mp.db.shop.tables.MedicalHistory.MEDICAL_HISTORY;


/**
 * @author 赵晓东
 * @description
 * @create 2020-07-07 10:40
 */
@Repository
public class MedicalHistoryDao extends ShopBaseDao {

    /**
     * 病历详情分页查询
     *
     * @param medicalHistoryListParam 病历详情入参
     * @return PageResult<MedicalHistoryListVo>
     */
    public MedicalHistoryListVo getMedicalHistoryDetail(MedicalHistoryListParam medicalHistoryListParam) {
        return db().select().from(MEDICAL_HISTORY)
            .where(MEDICAL_HISTORY.ID.eq(medicalHistoryListParam.getId())
                .and(MEDICAL_HISTORY.IS_DELETE.eq(DelFlag.NORMAL_VALUE))).fetchOneInto(MedicalHistoryListVo.class);
    }

    /**
     * 病历表列表查询
     *
     * @param medicalHistoryPageInfoParam 病历表列表入参
     * @return PageResult<MedicalHistoryPageInfoVo>
     */
    public PageResult<MedicalHistoryPageInfoVo> getMedicalHistoryPageInfo(MedicalHistoryPageInfoParam medicalHistoryPageInfoParam) {
        SelectConditionStep<Record> and = db().select().from(MEDICAL_HISTORY)
            .where(MEDICAL_HISTORY.PATIENT_ID.eq(medicalHistoryPageInfoParam.getPatientId())
                .and(MEDICAL_HISTORY.IS_DELETE.eq(DelFlag.NORMAL_VALUE)));
        return getPageResult(and, medicalHistoryPageInfoParam, MedicalHistoryPageInfoVo.class);
    }

    /**
     * 新增病历
     * @param fetchMedicalHistoryVo 病历入参
     */
    public void addHitsMedicalHistory(FetchMedicalHistoryVo fetchMedicalHistoryVo){
        MedicalHistoryRecord medicalHistoryRecord = db().newRecord(MEDICAL_HISTORY, fetchMedicalHistoryVo);
        medicalHistoryRecord.insert();
    }

    /**
     * 更新处方
     * @param fetchMedicalHistoryVo 处方入参
     */
    public void updateHitsMedicalHistory(FetchMedicalHistoryVo fetchMedicalHistoryVo){
        MedicalHistoryRecord medicalHistoryRecord = db().select().from(MEDICAL_HISTORY)
            .where(MEDICAL_HISTORY.ID.eq(fetchMedicalHistoryVo.getId()))
            .fetchOneInto(MedicalHistoryRecord.class);
        FieldsUtil.assign(fetchMedicalHistoryVo, medicalHistoryRecord);
        medicalHistoryRecord.update();
        fetchMedicalHistoryVo.setId(medicalHistoryRecord.getId());
    }
}
