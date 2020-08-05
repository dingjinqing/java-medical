package com.vpu.mp.service.shop.patient;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.DateUtils;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.RandomUtil;
import com.vpu.mp.common.foundation.util.Util;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestConstant;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.common.pojo.shop.table.PatientDo;
import com.vpu.mp.common.pojo.shop.table.UserPatientCoupleDo;
import com.vpu.mp.config.SmsApiConfig;
import com.vpu.mp.dao.shop.patient.PatientDao;
import com.vpu.mp.dao.shop.patient.UserPatientCoupleDao;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.foundation.jedis.JedisManager;
import com.vpu.mp.service.pojo.shop.patient.PatientConstant;
import com.vpu.mp.service.pojo.shop.patient.PatientExternalRequestParam;
import com.vpu.mp.service.pojo.shop.patient.PatientExternalVo;
import com.vpu.mp.service.pojo.shop.patient.PatientListParam;
import com.vpu.mp.service.pojo.shop.patient.PatientMoreInfoParam;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.PatientSimpleInfoVo;
import com.vpu.mp.service.pojo.shop.patient.PatientSmsCheckParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientDetailVo;
import com.vpu.mp.service.pojo.shop.patient.UserPatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import com.vpu.mp.service.pojo.shop.sms.template.SmsTemplate;
import com.vpu.mp.service.shop.config.BaseShopConfigService;
import com.vpu.mp.service.shop.sms.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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


    public UserPatientDetailVo getOneInfoForWx(Integer userId, Integer patientId) {
        UserPatientDetailVo patientInfo = userPatientCoupleDao.getUserPatientInfo(userId, patientId);
        if (patientInfo!=null){
            patientInfo.setAge(DateUtil.ageOfNow(patientInfo.getBirthday()));
        }
        return patientInfo;
    }
    public UserPatientDetailVo getOneInfoForWx(UserPatientParam userPatientParam) {
        return userPatientCoupleDao.getUserPatientInfo(userPatientParam);
    }

    public List<PatientOneParam> listPatientByUserId (Integer userId) {
        List<PatientOneParam> patientList = userPatientCoupleDao.listPatientIdsByUser(userId);
        return patientList;
    }

    public Integer defaultPatientId (Integer userId) {
        return userPatientCoupleDao.defaultPatientIdByUser(userId);
    }

    /**
     * 获取默认患者 没有为null
     * @param userId
     * @return
     */
    public UserPatientParam defaultPatientByUser(Integer userId) {
        return userPatientCoupleDao.defaultPatientByUser(userId);
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
    public UserPatientDetailVo getDefaultPatient(Integer userId){
        UserPatientParam param = userPatientCoupleDao.defaultPatientByUser(userId);
        return getOneDetail(param);
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

        List<PatientExternalVo> patientInfoVoList = Util.parseJson(apiExternalRequestResult.getData(), new TypeReference<List<PatientExternalVo>>(){});
        PatientExternalVo patientInfoVo=new PatientExternalVo();
        if(patientInfoVoList!=null&&patientInfoVoList.size()>0){
            patientInfoVo=patientInfoVoList.get(0);
        }
        PatientDo patientDo=new PatientDo();
        FieldsUtil.assignWithIgnoreField(patientInfoVo, patientDo,getPatientIgnoreFields());
        PatientOneParam patientOneParam = patientDao.getPatientByNameAndMobile(userPatientOneParam);
        if (patientOneParam == null) {
            patientDao.insertPatient(patientDo);
        } else {
            patientDo.setId(patientOneParam.getId());
            patientDao.updatePatient(patientDo);
        }
        UserPatientCoupleDo userPatientCoupleDo = new UserPatientCoupleDo();
        FieldsUtil.assign(patientDo,userPatientCoupleDo);
        userPatientCoupleDo.setId(null);
        userPatientCoupleDo.setPatientId(patientDo.getId());
        userPatientCoupleDo.setUserId(userPatientOneParam.getUserId());
        userPatientCoupleDo.setIsFetch(PatientConstant.FETCH);
        addPatientUser(userPatientCoupleDo);
        return JsonResult.success();
    }
    /**
     * 拷贝要忽略的字段
     * @return
     */
    private Set<String> getPatientIgnoreFields(){
        Set<String> ignoreField = new HashSet<>(2);
        ignoreField.add("createTime");
        ignoreField.add("lastUpdateTime");
        return ignoreField;
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
        if (diseaseStr == null || "".equals(diseaseStr)){
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
        if (diseaseStr == null || "".equals(diseaseStr)){
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
     * @param userPatientParam
     * @return
     */
    public UserPatientDetailVo getOneDetail(UserPatientParam userPatientParam) {
        if (userPatientParam != null && userPatientParam.getPatientId() != 0) {
            UserPatientDetailVo userPatientDetail = getOneInfoForWx(userPatientParam);
            if (userPatientDetail!=null){
                //根据出生日期获取年龄
                userPatientDetail.setAge(DateUtils.getAgeByBirthDay(userPatientDetail.getBirthday()));
                userPatientDetail.setDiseaseHistoryList(listDiseases(userPatientDetail.getDiseaseHistory()));
                userPatientDetail.setFamilyDiseaseHistoryList(listDiseases(userPatientDetail.getFamilyDiseaseHistory()));
                userPatientDetail.setDiseaseHistoryStr(strDisease(userPatientDetail.getDiseaseHistory()));
                userPatientDetail.setFamilyDiseaseHistoryStr(strDisease(userPatientDetail.getFamilyDiseaseHistory()));
                userPatientDetail.setId(userPatientDetail.getPatientId());
                return userPatientDetail;
            }
        }
        UserPatientDetailVo userPatientDetail = new UserPatientDetailVo();
        userPatientDetail.setDiseaseHistoryList(listDiseases(null));
        userPatientDetail.setFamilyDiseaseHistoryList(listDiseases(null));
        return userPatientDetail;
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

    public void addPatientUser(UserPatientCoupleDo userPatientCoupleDo) {
        List<PatientOneParam> patientList=userPatientCoupleDao.listPatientIdsByUser(userPatientCoupleDo.getUserId());
        if(patientList.size()==0) {
            userPatientCoupleDo.setIsDefault((byte) 1);
        }
//        userPatientCoupleDao.save(userPatientCoupleDo);
        saveUserPaitientCouple(userPatientCoupleDo);
    }

    public boolean isExistUserPatient(UserPatientParam param) {
        return userPatientCoupleDao.isExistUserPatient(param);
    }

    public void saveUserPaitientCouple(UserPatientCoupleDo userPatientCoupleDo) {
        UserPatientParam param = new UserPatientParam();
        FieldsUtil.assign(userPatientCoupleDo,param);
        Integer userPatientId = userPatientCoupleDao.getUserPatientId(param);
        if (userPatientId == null) {
            userPatientCoupleDao.save(userPatientCoupleDo);
        } else {
            userPatientCoupleDo.setId(userPatientId);
            userPatientCoupleDao.updateUserPatient(userPatientCoupleDo);
        }
    }

    /**
     * 接触用户患者关联
     * @param param
     */
    public void deleteUserPatient(UserPatientParam param) {
        userPatientCoupleDao.deleteUserPatient(param);
        userPatientCoupleDao.defaultPatientIdByUser(param.getUserId());
        if (userPatientCoupleDao.defaultPatientIdByUser(param.getUserId())==0) {
            List<PatientOneParam> patients = listPatientByUserId(param.getUserId());
            if (patients.size() > 0) {
                UserPatientParam userPatient = new UserPatientParam();
                FieldsUtil.assign(patients.get(0),userPatient);
                userPatientCoupleDao.setDefaultPatient(userPatient);
            }
        }
    }

    /**
     * 根据用户患者Id获取用户患者
     * @param param
     * @return
     */
    public UserPatientParam getUserPatient(UserPatientParam param) {
        return userPatientCoupleDao.getUserPatient(param);
    }
}
