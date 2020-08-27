package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.service.pojo.shop.rebate.InquiryOrderRebateListParam;
import com.vpu.mp.service.pojo.shop.rebate.InquiryOrderRebateVo;
import com.vpu.mp.service.pojo.shop.rebate.PrescriptionRebateListParam;
import com.vpu.mp.service.pojo.shop.rebate.PrescriptionRebateVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangpengcheng
 * @date 2020/8/27
 **/
@RestController
@RequestMapping("/api/admin/")
public class AdminDoctorRebateController extends AdminBaseController{

    /**
     * 处方返利列表查询
     * @param param
     * @return
     */
    @PostMapping("/doctor/rebate/prescription/list")
    public JsonResult prescriptionRebateList(@RequestBody PrescriptionRebateListParam param){
        PageResult<PrescriptionRebateVo> result=shop().prescriptionRebateService.getPageList(param);
        return success(result);
    }

    /**
     * 咨询返利列表查询
     * @param param
     * @return
     */
    @PostMapping("/doctor/rebate/inquiryOrder/list")
    public JsonResult inquiryOrderRebateList(@RequestBody InquiryOrderRebateListParam param){
        PageResult<InquiryOrderRebateVo> result=shop().inquiryOrderRebateService.getPageList(param);
        return success(result);
    }
}
