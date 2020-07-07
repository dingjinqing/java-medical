package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.patient.PatientListParam;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminPatientController extends AdminBaseController {
    @Override
    protected ShopApplication shop() {
        return saas.getShopApp(471752);
    }
    /**
     * 患者列表
     * @param param
     * @return
     */
    @PostMapping("/api/admin/patient/list")
    public JsonResult patientList(@RequestBody PatientListParam param) {
        PageResult<PatientOneParam> patientList = shop().patientService.getPatientList(param);
        return this.success(patientList);
    }

    /**
     * 根据id获取患者信息
     * @param patientId 患者id
     */
    @GetMapping("/api/admin/patient/{patientId}")
    public JsonResult getPatient(Integer patientId) {
        if (patientId == null) {
            return fail(JsonResultCode.DOCTOR_DEPARTMENT_ID_IS_NULL);
        }
        return success(shop().patientService.getOneInfo(patientId));
    }
}
