package com.vpu.mp.service.shop.patient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.common.pojo.shop.table.PatientDo;
import com.vpu.mp.common.pojo.shop.table.UserPatientCoupleDo;
import com.vpu.mp.dao.foundation.transactional.DbTransactional;
import com.vpu.mp.dao.foundation.transactional.DbType;
import com.vpu.mp.dao.shop.patient.PatientDao;
import com.vpu.mp.dao.shop.patient.UserPatientCoupleDao;
import com.vpu.mp.service.pojo.shop.patient.*;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author chenjie
 */
@Service
public class PatientService extends BaseShopConfigService{
    @Autowired
    protected PatientDao patientDao;
    @Autowired
    protected UserPatientCoupleDao userPatientCoupleDao;
    @Autowired
    public BaseShopConfigService baseShopConfigService;
    public static final int ZERO = 0;

    public PageResult<PatientOneParam> getPatientList(PatientListParam param) {
        PageResult<PatientOneParam> patientList = patientDao.getPatientList(param);

        return patientList;
    }

    public Integer insertPatient(PatientDo param) {
        transaction(() -> {
            int patientId = patientDao.insertPatient(param);
            param.setId(patientId);
        });
        return param.getId();
    }

    public Integer updatePatient(PatientDo param) {
        transaction(() -> {
            patientDao.updatePatient(param);
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

    public void setDefaultPatient (UserPatientParam userPatient) {
        userPatientCoupleDao.initDefaultUserPatient(userPatient.getUserId());
        userPatientCoupleDao.setDefaultPatient(userPatient);
    }

    /**
     * 拉取患者信息
     */
    @DbTransactional(type = DbType.SHOP_DB)
    public JsonResult getExternalPatientInfo(UserPatientOneParam userPatientOneParam){
        Integer shopId =getShopId();
        PatientExternalRequestParam requestParam=new PatientExternalRequestParam();
        requestParam.setName(userPatientOneParam.getName());
        requestParam.setIdentityCode(userPatientOneParam.getIdentityCode());
        requestParam.setMobile(userPatientOneParam.getMobile());
        String requestJson=Util.toJson(requestParam);
        ApiExternalRequestResult apiExternalRequestResult=saas().apiExternalRequestService.externalRequestGate(ApiExternalRequestConstant.APP_ID_HIS,shopId, ApiExternalRequestConstant.SERVICE_NAME_FETCH_PATIENT_INFO,requestJson);
        if(ApiExternalRequestConstant.ERROR_CODE.equals(apiExternalRequestResult.getError())) {
            JsonResult result = new JsonResult();
            result.setError(apiExternalRequestResult.getError());
            result.setMessage(apiExternalRequestResult.getMsg());
            result.setContent(apiExternalRequestResult.getData());
            return result;
        }
        PatientExternalVo patientInfoVo = Util.parseJson(apiExternalRequestResult.getData(), PatientExternalVo.class);
        PatientDo patientDo=new PatientDo();
        FieldsUtil.assign(patientInfoVo, patientDo);
        PatientOneParam patientOneParam = patientDao.getPatientByNameAndMobile(userPatientOneParam);
        if (patientOneParam == null) {
            int patientId=patientDao.insertPatient(patientDo);
            UserPatientCoupleDo userPatientCoupleDo=new UserPatientCoupleDo();
            userPatientCoupleDo.setPatientId(patientId);
            userPatientCoupleDo.setUserId(userPatientOneParam.getUserId());
            List<PatientOneParam> patientList=userPatientCoupleDao.listPatientIdsByUser(userPatientOneParam.getUserId());
            if(patientList.size()==0) {
                userPatientCoupleDo.setIsDefault((byte) 1);
            }
            userPatientCoupleDao.save(userPatientCoupleDo);
        } else {
            patientDo.setId(patientOneParam.getId());
            patientDao.updatePatient(patientDo);
        }
        return JsonResult.success();
    }

    /**
     * 根据姓名手机号身份证号查询患者信息
     * @param patientInfoParam
     * @return
     */
    public PatientOneParam getPatientByNameAndMobile(UserPatientOneParam patientInfoParam){
        return patientDao.getPatientByNameAndMobile(patientInfoParam);
    }

    /**
     * 获取疾病史选中List
     * @param diseaseStr
     * @return
     */
    public List<PatientMoreInfoParam> listDiseases(String diseaseStr) {
        List<PatientMoreInfoParam> diseaseList = Util.parseJson(get("diseases"), new TypeReference<List<PatientMoreInfoParam>>() {
        });
        if (diseaseStr == null || diseaseStr == "") return diseaseList;
        List<String> diseases = Arrays.asList(diseaseStr.split(","));
        for (PatientMoreInfoParam disease : diseaseList) {
            if (diseases.contains(disease.getId())) {
                disease.setChecked((byte) 1);
            }
        }
        return diseaseList;
    }

    /**
     * 获取患者详情信息(小程序前端)
     * @param patientId
     * @return
     */
    public PatientOneParam getOneDetail(Integer patientId) {
        if (patientId == 0) {
            PatientOneParam patientInfo = new PatientOneParam();
            patientInfo.setDiseaseHistoryList(listDiseases(null));
            patientInfo.setFamilyDiseaseHistoryList(listDiseases(null));
            return patientInfo;
        } else {
            PatientOneParam patientInfo = patientDao.getOneInfo(patientId);
            patientInfo.setDiseaseHistoryList(listDiseases(patientInfo.getDiseaseHistory()));
            patientInfo.setFamilyDiseaseHistoryList(listDiseases(patientInfo.getFamilyDiseaseHistory()));
            return patientInfo;
        }
    }

    /**
     * 获取患者信息
     * @param patientIds id集合
     * @return
     */
    public List<PatientSimpleInfoVo> listPatientInfo(List<Integer> patientIds){
        return patientDao.listPatientInfo(patientIds);
    }
}
