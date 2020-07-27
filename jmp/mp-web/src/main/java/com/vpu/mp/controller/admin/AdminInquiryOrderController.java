package com.vpu.mp.controller.admin;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderListParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderOnParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.vo.InquiryOrderDetailVo;
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
    public JsonResult payOrder(@RequestBody InquiryOrderOnParam inquiryOrderOnParam){
        if(inquiryOrderOnParam.getOrderId()==null){
            return fail(JsonResultCode.INQUIRY_ORDER_ID_IS_NULL);
        }
        InquiryOrderDetailVo inquiryOrderDetailVo= shop().inquiryOrderService.getDetailByOrderId(inquiryOrderOnParam.getOrderId());
        return success(inquiryOrderDetailVo);
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
