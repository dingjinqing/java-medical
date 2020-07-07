package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.doctor.DoctorListParam;
import com.vpu.mp.service.pojo.shop.doctor.DoctorOneParam;
import com.vpu.mp.service.shop.ShopApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminDoctorController extends AdminBaseController {
    @Override
    protected ShopApplication shop() {
        return saas.getShopApp(471752);
    }
    /**
     * 医师列表
     * @param param
     * @return
     */
    @PostMapping("/api/admin/doctor/list")
    public JsonResult doctorList(@RequestBody DoctorListParam param) {
        PageResult<DoctorOneParam> doctorList = shop().doctorService.getDoctorList(param);
        return this.success(doctorList);
    }

    /**
     *  医生新增
     * @param param {@link DoctorOneParam}
     */
    @PostMapping("/api/admin/doctor/add")
    public JsonResult insert(@RequestBody DoctorOneParam param) {
        if (param.getName()==null) {
            return fail(JsonResultCode.DOCTOR_DEPARTMENT_NAME_IS_NULL);
        }

//        boolean isExist = shop().departmentService.isNameExist(null,param.getName());
//        if (isExist) {
//            return fail(JsonResultCode.DOCTOR_DEPARTMENT_NAME_EXIST);
//        }

        shop().doctorService.insertDoctor(param);

        return success();
    }

    /**
     *  医生新增
     * @param param {@link DoctorOneParam}
     */
    @PostMapping("/api/admin/doctor/update")
    public JsonResult update(@RequestBody DoctorOneParam param) {
        if (param.getName()==null) {
            return fail(JsonResultCode.DOCTOR_DEPARTMENT_NAME_IS_NULL);
        }

//        boolean isExist = shop().departmentService.isNameExist(null,param.getName());
//        if (isExist) {
//            return fail(JsonResultCode.DOCTOR_DEPARTMENT_NAME_EXIST);
//        }

        shop().doctorService.updateDoctor(param);

        return success();
    }

    /**
     * 根据id获取医师信息
     * @param doctorId 医师id
     */
    @GetMapping("/api/admin/doctor/{doctorId}")
    public JsonResult getDoctor(Integer doctorId) {
        if (doctorId == null) {
            return fail(JsonResultCode.DOCTOR_DEPARTMENT_ID_IS_NULL);
        }
        return success(shop().doctorService.getOneInfo(doctorId));
    }

}
