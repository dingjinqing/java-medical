package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionListParam;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 药方
 * @author 孔德成
 * @date 2020/7/6 10:16
 */
@RestController
@RequestMapping(value = "/api/admin/prescription")
public class AdminPrescriptionController extends AdminBaseController {

    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * 处方分页
     * @param param
     */
    @PostMapping(value = "/list")
    public JsonResult listPageResult(@RequestBody @Validated PrescriptionListParam param){
        return success(prescriptionService.listPageResult(param));
    }
}
