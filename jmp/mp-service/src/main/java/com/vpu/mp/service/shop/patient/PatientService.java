package com.vpu.mp.service.shop.patient;

import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.*;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.common.pojo.shop.table.PatientDo;
import com.vpu.mp.common.pojo.shop.table.UserPatientCoupleDo;
import com.vpu.mp.config.SmsApiConfig;
import com.vpu.mp.dao.shop.patient.PatientDao;
import com.vpu.mp.dao.shop.patient.UserPatientCoupleDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.pojo.shop.patient.*;
import com.vpu.mp.service.pojo.shop.sms.template.SmsTemplate;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import com.vpu.mp.service.shop.sms.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    protected BaseShopConfigService baseShopConfigService;
    @Autowired
    protected SmsService smsService;
    @Autowired
    protected JedisManager jedisManager;

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
        patientDao.updatePatient(param);
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
     * 获取默认患者详情
     * @param userId
     * @return
     */
    public PatientOneParam getDefaultPatient(Integer userId){
        Integer patientId=userPatientCoupleDao.defaultPatientIdByUser(userId);
        return getOneDetail(patientId);
    }
    /**
     * 拉取患者信息
     */
    public JsonResult getExternalPatientInfo(UserPatientOneParam userPatientOneParam){
        boolean b = checkMobileCode(userPatientOneParam);
        if (!b){
            return null;
        }
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
            addPatientUser(patientId,userPatientOneParam.getUserId());
        } else {
            patientDo.setId(patientOneParam.getId());
            patientDao.updatePatient(patientDo);
        }
        return JsonResult.success();
    }

    /**
     * 短信验证码校验
     * @param param param
     * @return
     */
    private boolean checkMobileCode(UserPatientOneParam param) {
        String key = String.format(SmsApiConfig.REDIS_KEY_SMS_CHECK_PATIENT_MOBILE,getShopId(), param.getUserId(), param.getMobile());
        String s = jedisManager.get(key);
        if (!Strings.isBlank(s)&&!Strings.isBlank(param.getMobileCheckCode())){
            return s.equals(param.getMobileCheckCode());
        }
        return false;
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
        if (diseaseStr == null || diseaseStr.equals("")){
            return diseaseList;
        }
        List<String> diseases = Arrays.asList(diseaseStr.split(","));
        for (PatientMoreInfoParam disease : diseaseList) {
            if (diseases.contains(disease.getId())) {
                disease.setChecked((byte) 1);
            }
        }
        return diseaseList;
    }
    public String strDisease(String diseaseStr){
        List<PatientMoreInfoParam> diseaseList = Util.parseJson(get("diseases"), new TypeReference<List<PatientMoreInfoParam>>() {
        });
        if (diseaseStr == null || diseaseStr.equals("")){
            return "";
        }
        List<String> strList=new ArrayList<>();
        List<String> diseases = Arrays.asList(diseaseStr.split(","));
        for (PatientMoreInfoParam disease : diseaseList) {
            if (diseases.contains(disease.getId())) {
                strList.add(disease.getName());
            }
        }
        return StringUtils.join(strList.toArray(),",");
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
            //根据出生日期获取年龄
            patientInfo.setAge(DateUtils.getAgeByBirthDay(patientInfo.getBirthday()));
            patientInfo.setDiseaseHistoryList(listDiseases(patientInfo.getDiseaseHistory()));
            patientInfo.setFamilyDiseaseHistoryList(listDiseases(patientInfo.getFamilyDiseaseHistory()));
            patientInfo.setDiseaseHistoryStr(strDisease(patientInfo.getDiseaseHistory()));
            patientInfo.setFamilyDiseaseHistoryStr(strDisease(patientInfo.getFamilyDiseaseHistory()));
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

    /**
     * 发送短信校验码
     * @param param
     */
    public void sendCheckSms(PatientSmsCheckParam param) throws MpException {
        //0000-9999
        int intRandom = RandomUtil.getIntRandom();
        String smsContent = String.format(SmsTemplate.PATIENT_CHECK_MOBILE, "XX医院", intRandom);
        smsService.sendSms(param.getUserId(), param.getMobile(), smsContent);
        String key = String.format(SmsApiConfig.REDIS_KEY_SMS_CHECK_PATIENT_MOBILE, getShopId(), param.getUserId(), param.getMobile());
        jedisManager.set(key, intRandom + "", 600);
    }

    /**
     * 患者是否存在，用来新增检查
     * @param param
     * @return
     */
    public Integer getPatientExist(PatientExternalRequestParam param) {
        return patientDao.getPatientExist(param);
    }

    public void addPatientUser(Integer patientId,Integer userId) {
        UserPatientCoupleDo userPatientCoupleDo=new UserPatientCoupleDo();
        userPatientCoupleDo.setPatientId(patientId);
        userPatientCoupleDo.setUserId(userId);
        List<PatientOneParam> patientList=userPatientCoupleDao.listPatientIdsByUser(userId);
        if(patientList.size()==0) {
            userPatientCoupleDo.setIsDefault((byte) 1);
        }
        userPatientCoupleDao.save(userPatientCoupleDo);
    }

    public boolean isExistUserPatient(UserPatientParam param) {
        return userPatientCoupleDao.isExistUserPatient(param);
    }
}
