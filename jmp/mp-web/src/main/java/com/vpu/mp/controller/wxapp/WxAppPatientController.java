package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.pojo.shop.table.PatientDo;
import com.vpu.mp.common.pojo.shop.table.UserPatientCoupleDo;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.patient.*;
import com.vpu.mp.service.shop.patient.PatientService;
import com.vpu.mp.service.shop.prescription.FetchPrescriptionService;
import com.vpu.mp.service.shop.sms.SmsAccountService;
import com.vpu.mp.service.shop.sms.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.vpu.mp.common.foundation.data.JsonResultCode.CODE_SUCCESS;
import static com.vpu.mp.service.shop.prescription.FetchPatientInfoConstant.*;

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

    @Autowired
    private PatientService patientService;

    @Autowired
    private SmsService smsService;

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
        // 校验验证码
        boolean b = fetchPrescriptionService.checkMobileCode(userPatientOneParam);
        if (!b) {
            return fail(JsonResultCode.PATIENT_MOBILE_CHECK_CODE_ERROR);
        }
        // 拉取患者信息
        Integer info = fetchPrescriptionService.fetchPatientInfo(userPatientOneParam);
        if (FETCH_HIS_NO_PATIENT.equals(info)) {
            return fail(JsonResultCode.FETCH_HIS_NO_PATIENT);
        }
        return success();
    }

    /**
     * 刷新患者信息
     * @param userPatientWithoutCheckCodeParam 患者信息
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/user/patient/fetch/info")
    public JsonResult getPatientWithoutCheckCode(@RequestBody @Validated UserPatientWithoutCheckCodeParam userPatientWithoutCheckCodeParam) {
        boolean fetchPatient = fetchPrescriptionService.isFetchPatient(userPatientWithoutCheckCodeParam);
        // 如果没拉取过提示跳转至输入验证码界面
        if (fetchPatient) {
            return fail(JsonResultCode.TO_FETCH_PATIENT);
        } else { // 如果拉取过
            UserPatientOneParam userPatientOneParam = new UserPatientOneParam();
            FieldsUtil.assign(userPatientWithoutCheckCodeParam, userPatientOneParam);
            Integer info = fetchPrescriptionService.fetchPatientInfo(userPatientOneParam);
            if (FETCH_HIS_NO_PATIENT.equals(info)) {
                return fail(JsonResultCode.FETCH_HIS_NO_PATIENT);
            }
            return success();
        }
    }

    /**
     * 根据姓名手机号获取身份证号
     * @param userPatientFetchParam 用户信息入参
     * @return JsonResult
     */
    @PostMapping("/api/wxapp/user/patient/get/id")
    public JsonResult getPatientByNameAndMobile(@RequestBody UserPatientFetchParam userPatientFetchParam) {
        return success(fetchPrescriptionService.getPatientName(userPatientFetchParam));
    }

    /**
     * 发送短信校验
     * @return
     */
    @PostMapping("/api/wxapp/user/patient/send/sms")
    public JsonResult sendCheckSms(@RequestBody @Validated PatientSmsCheckNumParam param){
        // 判断该用户今日验证码发送是否超额
        JsonResultCode jsonResultCode = smsService.checkIsOutOfSmsNum(wxAppAuth.user().getUserId(), "");
        if (!jsonResultCode.equals(CODE_SUCCESS)) {
            return fail(jsonResultCode);
        }
        // 判断该患者今日验证码是否超额
        jsonResultCode = smsService.checkUserSmsNum(param);
        if (!jsonResultCode.equals(CODE_SUCCESS)) {
            return fail(jsonResultCode);
        }
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
