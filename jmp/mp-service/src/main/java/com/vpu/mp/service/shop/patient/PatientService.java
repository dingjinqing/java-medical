package com.vpu.mp.service.shop.patient;

import com.vpu.mp.common.foundation.util.PageResult;
import java.util.List;
import com.vpu.mp.dao.shop.patient.PatientDao;
import com.vpu.mp.dao.shop.patient.UserPatientCoupleDao;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.patient.PatientListParam;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService extends ShopBaseService{
    @Autowired
    protected PatientDao patientDao;
    @Autowired
    protected UserPatientCoupleDao userPatientCoupleDao;
    public static final int ZERO = 0;

    public PageResult<PatientOneParam> getPatientList(PatientListParam param) {
        PageResult<PatientOneParam> patientList = patientDao.getPatientList(param);

        return patientList;
    }

    public Integer insertDoctor(PatientOneParam param) {
        transaction(() -> {
            int doctorId = patientDao.insertPatient(param);
            param.setId(doctorId);
        });
        return param.getId();
    }

    public Integer updateDoctor(PatientOneParam param) {
        transaction(() -> {
            int doctorId = patientDao.updatePatient(param);
            param.setId(doctorId);
        });
        return param.getId();
    }

    public PatientOneParam getOneInfo(Integer patientId) {
        return patientDao.getOneInfo(patientId);
    }

    public List<PatientOneParam> listPatientByUserId (Integer userId) {
        List<PatientOneParam> patientList = userPatientCoupleDao.listPatientIdsByUser(userId);
        return patientList;
    }

    public Integer defaultPatientId (Integer userId) {
        return userPatientCoupleDao.defaultPatientIdByUser(userId);
    }
}
