package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.shop.order.write.operate.OrderServiceCode;
import com.vpu.mp.service.pojo.shop.order.write.operate.prescription.PrescriptionQueryParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序医师审核
 * @author 孔德成
 * @date 2020/7/28 9:35
 */
@RestController
public class WxAppDoctorAuditController extends WxAppBaseController{


    /**
     * 获取待审核处方
     * @return
     */
    @PostMapping("/api/wxapp/docker/audit/list")
    public JsonResult listAuditPrescription(@RequestBody PrescriptionQueryParam param) {
        param.setAction((byte)OrderServiceCode.PRESCRIPTION.ordinal());
        try {
            return success(shop().orderActionFactory.orderQuery(param));
        } catch (MpException e) {
            e.printStackTrace();
            return result(e.getErrorCode(), e.getErrorResult(), e.getCodeParamWrapper());

        }
    }
}
