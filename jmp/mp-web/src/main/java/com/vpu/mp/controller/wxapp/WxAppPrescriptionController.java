package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionListParam;
import com.vpu.mp.service.pojo.shop.prescription.PrescriptionNoParam;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * 处方信息
 * @author 孔德成
 * @date 2020/7/7 15:32
 */
@RestController
@RequestMapping("/api/wxapp/prescription")
public class WxAppPrescriptionController extends WxAppBaseController  {

    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * 获取处方类表
     */
    @PostMapping("/list")
    public JsonResult listPageResultWx(@RequestBody @Validated PrescriptionListParam param){
        return success(prescriptionService.listPageResultWx(param));
    }

    /**
     * 处方详情
     * @return
     */
    @PostMapping("/details")
    public JsonResult getPrescriptionDetails(@RequestBody @Validated PrescriptionNoParam param){
        return success(prescriptionService.getPrescriptionInfo(param.getPrescriptionNo()));
    }
}