package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.PatientDo;
import com.vpu.mp.common.pojo.shop.table.UserPatientCoupleDo;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.patient.PatientAddParam;
import com.vpu.mp.service.pojo.shop.patient.PatientConstant;
import com.vpu.mp.service.pojo.shop.patient.PatientExternalRequestParam;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.PatientSmsCheckParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientDetailVo;
import com.vpu.mp.service.pojo.shop.patient.UserPatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import com.vpu.mp.service.shop.prescription.FetchPrescriptionService;
import com.vpu.mp.service.shop.sms.SmsAccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.vpu.mp.service.shop.prescription.FetchPatientInfoConstant.FETCH_HITS_CHECK_CODE_ERROR;
import static com.vpu.mp.service.shop.prescription.FetchPatientInfoConstant.FETCH_HITS_NO_PATIENT;

/**
 * @author chenjie
 */
@RestController
@Slf4j
public class WxAppPatientController extends WxAppBaseController {
    @Autowired
    private SmsAccountService smsAccountService;

    @Autowired
    private FetchPrescriptionService fetchPrescriptionService;

    /**
     * 	获取用户的患者列表
     */
    @PostMapping("/api/wxapp/user/patient/list")
    public JsonResult getUserAccountWithdraw(@RequestBody UserPatientParam userPatient) {
        List<PatientOneParam> patientList = shop().patientService.listPatientByUserId(userPatient.getUserId());
        return success(patientList);
    }

    /**
     * 	切换默认患者
     */
    @PostMapping("/api/wxapp/user/patient/set/default")
    public JsonResult setDefaultPatient(@RequestBody UserPatientParam userPatient) {
        shop().patientService.setDefaultPatient(userPatient);
        return success();
    }

    /**
     * 	拉取患者信息
     */
    @PostMapping("/api/wxapp/user/patient/get/info")
    public JsonResult getPatientInfo(@RequestBody @Validated UserPatientOneParam userPatientOneParam) {
        Integer info = fetchPrescriptionService.fetchPatientInfo(userPatientOneParam);
        if (FETCH_HITS_CHECK_CODE_ERROR.equals(info)) {
            return fail(JsonResultCode.PATIENT_MOBILE_CHECK_CODE_ERROR);
        }
        if (FETCH_HITS_NO_PATIENT.equals(info)) {
            return fail(JsonResultCode.FETCH_HITS_NO_PATIENT);
        }
        return success();
    }

    /**
     * 发送短信校验
     * @return
     */
    @PostMapping("/api/wxapp/user/patient/send/sms")
    public JsonResult sendCheckSms(@RequestBody @Validated PatientSmsCheckParam param){
        param.setUserId(wxAppAuth.user().getUserId());
        try {
            shop().patientService.sendCheckSms(param);
        } catch (MpException e) {
            return fail();
        }
        return success();
    }

    /**
     * 	手动添加患者
     */
    @PostMapping("/api/wxapp/user/patient/add")
    public JsonResult addPatient(@RequestBody PatientAddParam patientAddParam) {
        Integer userId = patientAddParam.getUserId();
        PatientDo patientDo = new PatientDo();
        FieldsUtil.assign(patientAddParam,patientDo);
        if (patientDo.getId() >0) {
            shop().patientService.updatePatient(patientDo);
        } else {
            PatientExternalRequestParam param = new PatientExternalRequestParam();
            FieldsUtil.assign(patientDo,param);
            Integer patientId = shop().patientService.getPatientExist(param);
            if (patientId == null) {
                shop().patientService.insertPatient(patientDo);
            } else {
                patientDo.setId(patientId);
                UserPatientParam userPatientParam = new UserPatientParam();
                userPatientParam.setUserId(userId);
                userPatientParam.setPatientId(patientId);
                Boolean isExist = shop().patientService.isExistUserPatient(userPatientParam);
                if (isExist) {
                    return fail(JsonResultCode.PATIENT_IS_EXIST);
                }
                shop().patientService.updatePatient(patientDo);
            }
        }
        UserPatientCoupleDo userPatientCoupleDo = new UserPatientCoupleDo();
        FieldsUtil.assign(patientDo,userPatientCoupleDo);
        userPatientCoupleDo.setId(null);
        userPatientCoupleDo.setPatientId(patientDo.getId());
        userPatientCoupleDo.setUserId(userId);
        userPatientCoupleDo.setIsFetch(PatientConstant.FETCH);
        shop().patientService.addPatientUser(userPatientCoupleDo);
        return success();
    }

    /**
     * 	患者详情
     */
    @PostMapping("/api/wxapp/user/patient/get/detail")
    public JsonResult getPatientDetail(@RequestBody UserPatientParam param) {
        UserPatientDetailVo patientDetail = shop().patientService.getOneDetail(param);
        return success(patientDetail);
    }

    /**
     * 获取用户默认患者信息
     * @return
     */
    @PostMapping("/api/wxapp/user/patient/get/default")
    public JsonResult getDefaultPatient(){
        Integer userId=wxAppAuth.user().getUserId();
        return success(shop().patientService.getDefaultPatient(userId));
    }

    /**
     * 	手动删除患者
     */
    @PostMapping("/api/wxapp/user/patient/delete")
    public JsonResult deletePatient(@RequestBody UserPatientParam param) {
        shop().patientService.deleteUserPatient(param);
        return success();
    }
}
