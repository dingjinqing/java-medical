package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.medical.FetchMedicalAdviceParam;
import com.vpu.mp.service.pojo.shop.medicalhistory.FetchMedicalHistoryParam;
import com.vpu.mp.service.pojo.shop.medicalhistory.MedicalHistoryListParam;
import com.vpu.mp.service.pojo.shop.medicalhistory.MedicalHistoryPageInfoParam;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionOneParam;
import com.vpu.mp.service.pojo.shop.prescription.FetchPrescriptionParam;
import com.vpu.mp.service.shop.medicine.MedicalAdviceService;
import com.vpu.mp.service.shop.prescription.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 赵晓东
 * @description 小程序病历接口
 * @create 2020-07-09 09:03
 */
@RestController
@RequestMapping(value = "/api/wxapp/medicine/history")
public class WxAppMedicalHistoryController extends WxAppBaseController {

    @Autowired
    private MedicalAdviceService medicalAdviceService;

    @Autowired
    private PrescriptionService prescriptionService;

    /**
     * 病历详情
     * @param medicalHistoryListParam 病历详情入参
     * @return JsonResult
     */
    @RequestMapping("/detail")
    public JsonResult medicalHistoryDetail(@RequestBody MedicalHistoryListParam medicalHistoryListParam) {
        return success(shop().medicalHistoryService.getMedicalHistoryDetail(medicalHistoryListParam));
    }

    /**
     * 分页展示病历列表
     * @param medicalHistoryPageInfoParam 病历列表入参
     * @return JsonResult
     */
    @RequestMapping("/list")
    public JsonResult getHistoryInfo(@RequestBody MedicalHistoryPageInfoParam medicalHistoryPageInfoParam) {
        return success(shop().medicalHistoryService.getMedicalHistoryPageInfo(medicalHistoryPageInfoParam));
    }

    /**
     * 拉取病历处方
     * @param param 病历列表入参
     * @return JsonResult
     */
    @RequestMapping("/get/external/list")
    public JsonResult pullHistoryPrescription(@RequestBody FetchMedicalHistoryParam param) {
        return success(shop().pullHitsHistoryPrescriptionService.pullExternalHistoryPrescription(param));
    }

    /**
     * 拉取hits系统医嘱明细信息
     * @param fetchMedicalAdviceParam 拉取医嘱明细
     * @return JsonResult
     */
    @RequestMapping("/fetch/external/medical/advice")
    public JsonResult test1(@RequestBody FetchMedicalAdviceParam fetchMedicalAdviceParam){
        return this.success(medicalAdviceService.pullExternalMedicalAdviceList(fetchMedicalAdviceParam));
    }

    /**
     * 拉取hits系统处方列表信息
     * @param fetchPrescriptionParam 拉取处方列表
     * @return JsonResult
     */
    @RequestMapping("/fetch/prescription")
    public JsonResult test2(@RequestBody FetchPrescriptionParam fetchPrescriptionParam){
        return this.success(prescriptionService.pullExternalAllPrescriptionInfo(fetchPrescriptionParam));
    }

    /**
     * 根据患者编号拉取处方
     * @param fetchPrescriptionParam 拉取单条处方
     * @return JsonResult
     */
    @RequestMapping("/fetch/prescription/item")
    public JsonResult test3(@RequestBody FetchPrescriptionOneParam fetchPrescriptionParam){
        return this.success(prescriptionService.pullExternalOnePrescriptionInfo(fetchPrescriptionParam));
    }
}
