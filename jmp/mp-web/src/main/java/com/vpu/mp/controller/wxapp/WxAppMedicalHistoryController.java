package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.pojo.shop.medicalHistory.MedicalHistoryListParam;
import com.vpu.mp.service.pojo.shop.medicalHistory.MedicalHistoryPageInfoParam;
import com.vpu.mp.service.shop.medicine.MedicalHistoryService;
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
}
