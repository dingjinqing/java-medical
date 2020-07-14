package com.vpu.mp.dao.shop.patient;

import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.patient.PatientConstant;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.PATIENT;
import static com.vpu.mp.db.shop.Tables.USER;
import static com.vpu.mp.db.shop.Tables.USER_PATIENT_COUPLE;

@Repository
public class UserPatientCoupleDao  extends ShopBaseDao {

    /**
     * 获取默认患者 没有为0
     * @param userId
     * @return
     */
    public Integer defaultPatientIdByUser(Integer userId) {
        Integer patientId = db().select(USER_PATIENT_COUPLE.PATIENT_ID).from(USER_PATIENT_COUPLE).where(USER_PATIENT_COUPLE.USER_ID.eq(userId).and(USER_PATIENT_COUPLE.IS_DEFAULT.eq(PatientConstant.Default)))
            .fetchOptional(USER_PATIENT_COUPLE.PATIENT_ID, int.class).orElse(0);
        return patientId;
    }

    public List<PatientOneParam> listPatientIdsByUser(Integer userId) {
        List<PatientOneParam> patientList = db().select(USER_PATIENT_COUPLE.IS_DEFAULT,PATIENT.asterisk()).from(USER_PATIENT_COUPLE)
            .leftJoin(PATIENT).on(PATIENT.ID.eq(USER_PATIENT_COUPLE.PATIENT_ID))
            .where(USER_PATIENT_COUPLE.USER_ID.eq(userId).and(PATIENT.IS_DELETE.eq((byte) 0)))
            .fetchInto(PatientOneParam.class);
        return patientList;
    }
}
