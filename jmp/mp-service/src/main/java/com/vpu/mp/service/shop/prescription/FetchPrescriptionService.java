package com.vpu.mp.service.shop.prescription;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.config.SmsApiConfig;
import com.vpu.mp.dao.shop.patient.PatientDao;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.foundation.service.ShopBaseService;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientFetchParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientFetchVo;
import com.vpu.mp.service.pojo.shop.patient.UserPatientOneParam;
import com.vpu.mp.service.shop.medicine.MedicalAdviceService;
import com.vpu.mp.service.shop.medicine.MedicalHistoryService;
import com.vpu.mp.service.shop.patient.PatientService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.vpu.mp.service.shop.prescription.FetchPatientInfoConstant.*;

/**
 * @author 赵晓东
 * @description
 * @create 2020-08-13 18:21
 **/

@Service
public class FetchPrescriptionService extends ShopBaseService {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicalHistoryService medicalHistoryService;

    @Autowired
    private MedicalAdviceService medicalAdviceService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private JedisManager jedisManager;

    @Autowired
    private PatientDao patientDao;

    /**
     * 拉取用户病历，医嘱，处方，患者信息
     *
     * @param userPatientOneParam 用户信息
     * @return Boolean
     */
    public Integer fetchPatientInfo(UserPatientOneParam userPatientOneParam) {
        // 判断是否是拉取过来的患者
        PatientOneParam patientByName = patientDao.getPatientByName(userPatientOneParam);
        if (patientByName == null) {
            return FETCH_HITS_NO_PATIENT;
        }
        if (NO_FETCH.equals(patientByName.getIsFetch())) {
            return START_TO_COMMIT;
        }
        if (ALREADY_FETCH.equals(patientByName.getIsFetch())) {
            // 验证码
            boolean b = this.checkMobileCode(userPatientOneParam);
            if (!b) {
                return FETCH_HITS_CHECK_CODE_ERROR;
            }
        }
        JsonResult externalPatientInfo = patientService.getExternalPatientInfo(userPatientOneParam);
        JsonResult medicalAdviceList = medicalAdviceService.pullExternalMedicalAdviceList(userPatientOneParam);
        JsonResult medicalHistoryList = medicalHistoryService.pullExternalMedicalHistoryList(userPatientOneParam);
        JsonResult prescriptionInfo = prescriptionService.pullExternalAllPrescriptionInfo(userPatientOneParam);
        if (externalPatientInfo.getError() == 0) {
            return FETCH_HITS_SUCCESS;
        }
        return FETCH_HITS_NO_PATIENT;
    }

    /**
     * 短信验证码校验
     *
     * @param param param
     * @return
     */
    private boolean checkMobileCode(UserPatientOneParam param) {
        String key = String.format(SmsApiConfig.REDIS_KEY_SMS_CHECK_PATIENT_MOBILE, getShopId(), param.getUserId(), param.getMobile());
        String s = jedisManager.get(key);
        if (!Strings.isBlank(s) && !Strings.isBlank(param.getMobileCheckCode())) {
            return s.equals(param.getMobileCheckCode());
        }
        return false;
    }

    /**
     * 根据姓名手机号获取身份证号
     * @param userPatientOneParam 患者入参
     * @return PatientOneParam
     */
    public UserPatientFetchVo getPatientName(UserPatientFetchParam userPatientOneParam) {
        PatientOneParam patientByName = patientDao.getPatientByName(userPatientOneParam);
        UserPatientFetchVo userPatientFetchVo = new UserPatientFetchVo();
        userPatientFetchVo.setName(patientByName.getName());
        userPatientFetchVo.setMobile(patientByName.getMobile());
        userPatientFetchVo.setIdentityCode(patientByName.getIdentityCode());
        return userPatientFetchVo;
    }
}