package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.department.DepartmentListParam;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorConsultationOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorConsultationParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorConsultationVo;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import com.vpu.mp.service.pojo.shop.title.TitleOneParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public class WxAppDoctorConsultationController extends WxAppBaseController {
    /**
     * 	获取医师科室列表
     */
    @PostMapping("/api/wxapp/department/list")
    public JsonResult getDoctorDepartmentList(@RequestBody DepartmentListParam departmentListParam) {
        List<DepartmentOneParam> departmentList = shop().departmentService.listDepartmentsByName(departmentListParam.getKeyword());
        return success(departmentList);
    }

    /**
     * 	获取医师列表(页面)
     */
    @PostMapping("/api/wxapp/doctor/list")
    public JsonResult getDoctorList(@RequestBody DoctorConsultationParam doctorParam) {
        List<DoctorConsultationOneParam> doctorList = shop().doctorService.listDoctorForConsultation(doctorParam);
        List<DepartmentOneParam> departmentList = shop().departmentService.listDepartmentsByName(null);
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
