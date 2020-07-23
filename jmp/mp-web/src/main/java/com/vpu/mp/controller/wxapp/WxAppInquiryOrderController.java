package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.RequestUtil;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderListParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderOnParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryToPayParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/*
*问诊订单
 */
@RestController
public class WxAppInquiryOrderController extends WxAppBaseController{
    /**
     * 问诊订单支付
     * @return
     */
    @PostMapping("/api/wxapp/inquiry/order/pay")
    public JsonResult payOrder(@RequestBody @Validated InquiryToPayParam payParam){
        WxAppSessionUser user = wxAppAuth.user();
        payParam.setUser(user);
        payParam.setClientIp(RequestUtil.getIp(request));
        return success(shop().inquiryOrderService.payInquiryOrder(payParam));
    }
    /**
     * 问诊订单退款
     */
    @PostMapping("/api/wxapp/inquiry/order/refund")
    public JsonResult refund(@RequestBody InquiryOrderOnParam inquiryOrderOnParam){
        JsonResult result= shop().inquiryOrderService.refund(inquiryOrderOnParam);
        return result;
    }
    /**
     * 查询问诊订单
     */
    @PostMapping("/api/wxapp/inquiry/order/list")
    public JsonResult refundOrder(@RequestBody InquiryOrderListParam param){
        PageResult<InquiryOrderDo> list=shop().inquiryOrderService.getInquiryOrderList(param);
        return success(list);
    }
    /**
     * 获取订单详情
     */
    @PostMapping("/api/wxapp/inquiry/order/detail")
    public JsonResult payOrder(@RequestBody @Validated InquiryOrderOnParam inquiryOrderOnParam){
        InquiryOrderDo inquiryOrderDo= shop().inquiryOrderService.getByOrderSn(inquiryOrderOnParam.getOrderSn());
        return success(inquiryOrderDo);
    }
    /**
     * 更改问诊订单状态
     */
    @PostMapping("/api/wxapp/inquiry/order/status/update")
    public JsonResult updateStatus(@RequestBody @Validated InquiryOrderOnParam inquiryOrderOnParam){
        shop().inquiryOrderService.updateOrderReceiving(inquiryOrderOnParam);
        return success();
    }



}
