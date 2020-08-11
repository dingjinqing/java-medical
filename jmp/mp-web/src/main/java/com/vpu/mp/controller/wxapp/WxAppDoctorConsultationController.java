package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.department.DepartmentIdNameVo;
import com.vpu.mp.service.pojo.shop.department.DepartmentListParam;
import com.vpu.mp.service.pojo.shop.department.DepartmentOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorConsultationOneParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorConsultationParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorConsultationVo;
import com.vpu.mp.service.pojo.shop.doctor.DoctorRecommendVo;
import com.vpu.mp.service.pojo.shop.patient.UserPatientParam;
import com.vpu.mp.service.pojo.shop.title.TitleOneParam;
import com.vpu.mp.service.pojo.shop.user.user.UserDoctorParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author chenjie
 */
@RestController
public class WxAppDoctorConsultationController extends WxAppBaseController {
    /**
     * 	获取医师科室列表
     */
    @PostMapping("/api/wxapp/department/list")
    public JsonResult getDoctorDepartmentList(@RequestBody DepartmentListParam departmentListParam) {
        List<DepartmentOneParam> departmentList = shop().departmentService.listDepartmentsByOptions(departmentListParam);
        return success(departmentList);
    }

    /**
     * 	获取医师列表(页面)
     */
    @PostMapping("/api/wxapp/doctor/list")
    public JsonResult getDoctorList(@RequestBody DoctorConsultationParam doctorParam) {
        PageResult<DoctorConsultationOneParam> doctorList = shop().doctorService.listDoctorForConsultation(doctorParam);
        List<DepartmentOneParam> departmentList = shop().departmentService.listDepartmentsSelect();
        List<TitleOneParam> titleList = shop().titleService.listTitlesSelect();
        DoctorConsultationVo data = new DoctorConsultationVo();
        data.setDepartmentList(departmentList);
        data.setTitleList(titleList);
        data.setDoctorList(doctorList);
        return success(data);
    }

    /**
     * 	获取推荐医师列表(页面)
     */
    @PostMapping("/api/wxapp/recommend/doctor/list")
    public JsonResult getDoctorList(@RequestBody UserPatientParam doctorParam) {
        doctorParam.setPatientId(shop().patientService.defaultPatientId(doctorParam.getUserId()));
        List<DepartmentIdNameVo> recommendDepartment = shop().departmentService.listRecommendDepartment();
        List<DoctorConsultationOneParam> doctorList = shop().doctorService.listRecommendDoctorForConsultation(doctorParam);
        DoctorRecommendVo data = new DoctorRecommendVo();
        data.setDoctorList(doctorList);
        data.setRecommendDepartment(recommendDepartment);
        return success(data);
    }

    /**
     * 	取消关注
     */
    @PostMapping("/api/wxapp/user/doctor/delete")
    public JsonResult deleteAttention(@RequestBody UserDoctorParam param) {
        shop().doctorService.deleteUserDoctor(param);
        return success();
    }

    /**
     * 	添加关注
     */
    @PostMapping("/api/wxapp/user/doctor/add")
    public JsonResult addAttention(@RequestBody UserDoctorParam param) {
        shop().doctorService.insertUserDoctor(param);
        return success();
    }
}
