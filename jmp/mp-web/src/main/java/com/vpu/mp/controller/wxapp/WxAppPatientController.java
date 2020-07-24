package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.common.pojo.shop.table.PatientDo;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.patient.PatientExternalRequestParam;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.PatientSmsCheckParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import com.vpu.mp.service.pojo.shop.sms.SmsAccountParam;
import com.vpu.mp.service.shop.ShopApplication;
import com.vpu.mp.service.shop.sms.SmsAccountService;
import com.vpu.mp.service.shop.sms.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenjie
 */
@RestController
@Slf4j
public class WxAppPatientController extends WxAppBaseController {
    @Autowired
    private SmsAccountService smsAccountService;

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
    public JsonResult getPatientInfo(@RequestBody @Validated UserPatientOneParam userPatientOneParam){
        JsonResult result= shop().patientService.getExternalPatientInfo(userPatientOneParam);
        if (result==null){
            return fail(JsonResultCode.PATIENT_MOBILE_CHECK_CODE_ERROR);
        }
        return result;
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
    public JsonResult addPatient(@RequestBody PatientDo patientDo) {
        if (patientDo.getId() >0) {
            shop().patientService.updatePatient(patientDo);
        } else {
            shop().patientService.insertPatient(patientDo);
        }
        return success();
    }

    /**
     * 	患者详情
     */
    @PostMapping("/api/wxapp/user/patient/get/detail")
    public JsonResult getPatientDetail(@RequestBody PatientOneParam patientOneParam) {
        PatientOneParam patientDetail = shop().patientService.getOneDetail(patientOneParam.getId());
        return success(patientDetail);
    }

    /**
     * 测试
     * @param param
     * @return
     */
    @PostMapping("/api/wxapp/user/sms/add")
    public JsonResult createSmsAccount(@RequestBody  SmsAccountParam param){
        String smsAccount = null;
        try {
            smsAccount = smsAccountService.createSmsAccount(param);
        } catch (MpException e) {
            e.printStackTrace();
            return fail();
        }
        return success(smsAccount);
    }
}
