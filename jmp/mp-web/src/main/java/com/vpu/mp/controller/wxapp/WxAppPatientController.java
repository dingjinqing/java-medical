package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class WxAppPatientController extends WxAppBaseController {
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
}