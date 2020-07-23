package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryOrderListParam;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryOrderOnParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问诊订单
 * @author yangpengcheng
 */
@RestController
public class AdminInquiryOrderController extends AdminBaseController{
    /**
     * 获取订单详情
     */
    @PostMapping("/api/admin/inquiry/order/detail")
    public JsonResult payOrder(@RequestBody @Validated InquiryOrderOnParam inquiryOrderOnParam){
        InquiryOrderDo inquiryOrderDo= shop().inquiryOrderService.getByOrderId(inquiryOrderOnParam.getOrderId());
        return success(inquiryOrderDo);
    }
    /**
     * 查询问诊订单
     */
    @PostMapping("/api/admin/inquiry/order/list")
    public JsonResult refundOrder(@RequestBody InquiryOrderListParam param){
        PageResult<InquiryOrderDo> list=shop().inquiryOrderService.getInquiryOrderList(param);
        return success(list);
    }
    /**
     * 问诊订单退款
     */
    @PostMapping("/api/admin/inquiry/order/refund")
    public JsonResult refund(@RequestBody InquiryOrderOnParam inquiryOrderOnParam){
        JsonResult result= shop().inquiryOrderService.refund(inquiryOrderOnParam);
        return result;
    }
}
