package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class WxAppPatientController extends WxAppBaseController {
    /**
     * 	获取用户的患者列表
     */
    @GetMapping("/api/wxapp/user/patient/list/{userId}")
    public JsonResult getUserAccountWithdraw(@PathVariable Integer userId) {
        List<PatientOneParam> patientList = shop().patientService.listPatientByUserId(userId);
        return success(patientList);
    }
}
