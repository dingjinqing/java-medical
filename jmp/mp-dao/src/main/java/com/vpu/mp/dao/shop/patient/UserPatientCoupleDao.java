package com.vpu.mp.dao.shop.patient;

import com.vpu.mp.dao.foundation.base.ShopBaseDao;
import com.vpu.mp.service.pojo.shop.patient.PatientConstant;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.vpu.mp.db.shop.Tables.USER_PATIENT_COUPLE;

@Repository
public class UserPatientCoupleDao  extends ShopBaseDao {

    public Integer defaultPatientIdByUser(Integer userId) {
        Integer patientId = db().select().from(USER_PATIENT_COUPLE).where(USER_PATIENT_COUPLE.USER_ID.eq(userId).and(USER_PATIENT_COUPLE.IS_DEFAULT.eq(PatientConstant.Default)))
            .fetchOne(0, int.class);
        return patientId;
    }

    public List<Integer> listPatientIdsByUser(Integer userId) {
        List<Integer> patientIds = db().select().from(USER_PATIENT_COUPLE).where(USER_PATIENT_COUPLE.USER_ID.eq(userId))
            .fetch().into(Integer.class);
        return patientIds;
    }
}
