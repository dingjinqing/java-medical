package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.saas.api.ApiExternalRequestResult;
import com.vpu.mp.common.pojo.shop.table.PatientDo;
import com.vpu.mp.service.pojo.shop.department.DepartmentListParam;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.*;
import com.vpu.mp.service.pojo.shop.patient.PatientExternalRequestParam;
import com.vpu.mp.service.pojo.shop.patient.PatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientOneParam;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import com.vpu.mp.service.pojo.shop.title.TitleOneParam;
import com.vpu.mp.service.shop.ShopApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author chenjie
 */
@RestController
@Slf4j
public class WxAppDoctorController extends WxAppBaseController {
    /**
     * 	获取医师科室列表
     */
    @PostMapping("/api/wxapp/department/list")
    public JsonResult getDoctorDepartmentList(@RequestBody DepartmentListParam departmentListParam) {
        List<DepartmentOneParam> departmentList = shop().departmentService.ListDepartmentsByName(departmentListParam.getKeyword());
        return success(departmentList);
    }

    /**
     * 	获取医师列表(页面)
     */
    @PostMapping("/api/wxapp/doctor/list")
    public JsonResult getDoctorList(@RequestBody DoctorConsultationParam doctorParam) {
        List<DoctorConsultationOneParam> doctorList = shop().doctorService.listDoctorForConsultation(doctorParam);
        List<DepartmentOneParam> departmentList = shop().departmentService.ListDepartmentsByName(null);
        List<TitleOneParam> titleList = shop().titleService.listTitles();
        DoctorConsultationVo data = new DoctorConsultationVo();
        data.setDepartmentList(departmentList);
        data.setTitleList(titleList);
        data.setDoctorList(doctorList);
        return success(data);
    }

    /**
     * 	获取医师列表(页面)
     */
    @PostMapping("/api/wxapp/recommend/doctor/list")
    public JsonResult getDoctorList(@RequestBody UserPatientParam doctorParam) {
        List<DoctorConsultationOneParam> doctorList = shop().doctorService.listRecommendDoctorForConsultation(doctorParam);
        return success(doctorList);
    }
}
