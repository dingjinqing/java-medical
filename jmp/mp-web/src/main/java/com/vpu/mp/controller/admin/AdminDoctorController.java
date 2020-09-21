package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.FieldsUtil;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.department.DepartmentListVo;
import com.vpu.mp.service.pojo.shop.doctor.*;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.audit.DoctorAuditedPrescriptionParam;
import com.vpu.mp.service.shop.doctor.DoctorStatisticService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenjie
 */
@RestController
public class AdminDoctorController extends AdminBaseController {
    @Autowired
    private PrescriptionService prescriptionService;
    @Autowired
    private DoctorStatisticService doctorStatisticService;
    /**
     * 医师列表
     * @param param
     * @return
     */
    @PostMapping("/api/admin/doctors/list")
    public JsonResult doctorList(@RequestBody DoctorListParam param) {
        PageResult<DoctorOneParam> doctorList = shop().doctorService.getDoctorList(param);
        return this.success(doctorList);
    }

    /**
     *  医生新增
     * @param param {@link DoctorOneParam}
     */
    @PostMapping("/api/admin/doctors/add")
    public JsonResult insert(@RequestBody DoctorOneParam param) {
        if (param.getName()==null) {
            return fail(JsonResultCode.DOCTOR_NAME_IS_NULL);
        }

        boolean isExist = shop().doctorService.isCodeExist(null,param.getHospitalCode());
        if (isExist) {
            return fail(JsonResultCode.DOCTOR_CODE_IS_EXIST);
        }

        shop().doctorService.insertDoctor(param);

        return success();
    }

    /**
     *  医生新增
     * @param param {@link DoctorOneParam}
     */
    @PostMapping("/api/admin/doctors/update")
    public JsonResult update(@RequestBody DoctorOneParam param) {
        if (param.getId()==null) {
            return fail(JsonResultCode.DOCTOR_ID_IS_NULL);
        }

        boolean isExist = shop().doctorService.isCodeExist(param.getId(),param.getHospitalCode());
        if (isExist) {
            return fail(JsonResultCode.DOCTOR_CODE_IS_EXIST);
        }

        shop().doctorService.updateDoctor(param);

        return success();
    }

    /**
     * 根据id获取医师信息
     * @param doctorId 医师id
     */
    @GetMapping("/api/admin/doctors/info/{doctorId}")
    public JsonResult getDoctor(@PathVariable Integer doctorId) {
        if (doctorId == null) {
            return fail(JsonResultCode.DOCTOR_ID_IS_NULL);
        }
        DoctorOneParam doctorInfo = shop().doctorService.getOneInfo(doctorId);
        if (doctorInfo.getTitleId() == 0) {
            doctorInfo.setTitleId(null);
        }
        return success(doctorInfo);
    }

    /**
     * 医生停用启用
     *
     * @param param {@link DoctorOneParam}
     */
    @PostMapping("/api/admin/doctors/enable")
    public JsonResult enableDoctor(@RequestBody DoctorOneParam param) {
        if (param.getId() == null) {
            return fail(JsonResultCode.DOCTOR_ID_IS_NULL);
        }
        shop().doctorService.enableDoctor(param);
        return success();
    }

    /**
     *  医师拉取
     */
    @PostMapping("/api/admin/doctors/fetch")
    public JsonResult fetchDoctors() {
        return shop().doctorService.fetchExternalDoctor();
    }

    /**
     * 医师下拉列表
     * @param param
     * @return
     */
    @PostMapping("/api/admin/doctors/select/list")
    public JsonResult doctorSelectList(@RequestBody DoctorListParam param) {
        List<DoctorOneParam> doctorList = shop().doctorService.getSelectDoctorList(param);
        return this.success(doctorList);
    }

    /**
     * 医师解绑
     * @param doctorUnbundlingParam doctorId
     * @return JsonResult
     */
    @PostMapping("/api/admin/doctor/unbundling")
    public JsonResult doctorUnbundling (@RequestBody DoctorUnbundlingParam doctorUnbundlingParam) {
        shop().doctorService.doctorUnbundling(doctorUnbundlingParam);
        return success();
    }

    /**
     * 医师停止接诊
     * @param doctorUnbundlingParam 医师id
     * @return JsonResult
     */
    @PostMapping("/api/admin/doctor/consultation")
    public JsonResult doctorCanConsultation(@RequestBody DoctorUnbundlingParam doctorUnbundlingParam) {
        shop().doctorService.doctorCanConsultation(doctorUnbundlingParam);
        return success();
    }

    /**
     *
     * @return
     */
    @PostMapping("/api/admin/doctor/")
    public JsonResult listDoctorComment(@RequestBody @Validated DoctorAuditedPrescriptionParam param){
        return success(prescriptionService.auditedPrescriptionList(param));
    }

    /**
     *
     * @return
     */
    @PostMapping("/api/admin/doctor/test")
    public JsonResult testDoctor(@RequestBody @Validated DoctorAuditedPrescriptionParam param){
        shop().doctorService.testTemplateMessage();
        return success();
    }

    /**
     * 查看医师详情
     * @param doctorUnbundlingParam doctorId
     * @return JsonResult
     */
    @PostMapping("/api/admin/doctors/details/show")
    public JsonResult adminDoctorDetails(@RequestBody DoctorUnbundlingParam doctorUnbundlingParam) {
        // 医师科室
        List<DepartmentListVo> departmentListVos =
            shop().doctorService.selectDepartmentsByDoctorId(doctorUnbundlingParam.getDoctorId());
        List<String> departmentNames = departmentListVos.stream().map(DepartmentListVo::getName).collect(Collectors.toList());
        DoctorOneParam oneInfo = shop().doctorService.getOneInfo(doctorUnbundlingParam.getDoctorId());
        //添加医师职称
        String title = shop().doctorService.selectDoctorTitle(oneInfo);
        oneInfo.setTitleName(title);
        oneInfo.setDepartmentNames(departmentNames);
        return success(oneInfo);
    }

    /**
     * 医师统计列表
     * @param param
     * @return
     */
    @PostMapping("/api/admin/doctors/summary/list")
    public JsonResult doctorSummaryList(@RequestBody DoctorStatisticParam param) {
        PageResult<DoctorStatisticListVo> doctorSummaryList = shop().doctorStatisticService.getDoctorSummaryList(param);
        return this.success(doctorSummaryList);
    }

    /**
     * 医师业绩统计列表
     * @param param
     * @return
     */
    @PostMapping("/api/admin/doctors/attendance/summary/list")
    public JsonResult doctorAttendanceSummaryList(@RequestBody DoctorAttendanceListParam param) {
        PageResult<DoctorAttendanceOneParam> doctorAttendanceSummaryList = shop().doctorLoginLogService.getDoctorAttendancePage(param);
        return this.success(doctorAttendanceSummaryList);
    }

    /**
     * 医师业绩统计分布
     * @param param
     * @return
     */
    @PostMapping("/api/admin/doctors/attendance/summary/divide")
    public JsonResult doctorAttendanceSummaryDivide(@RequestBody DoctorAttendanceListParam param) {
        DoctorAttendanceDivideVo doctorAttendanceSummaryDivide = shop().doctorLoginLogService.getDoctorAttendanceDivide(param.getType());
        return this.success(doctorAttendanceSummaryDivide);
    }

    /**
     * 医师业绩统计分布
     * @return
     */
    @PostMapping("/api/admin/doctors/summary/test")
    public JsonResult doctorSummaryTest() {
        doctorStatisticService.doctorStatistics();
        return this.success();
    }
}
