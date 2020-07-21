package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.RequestUtil;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryOrderListParam;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryOrderOnParam;
import com.vpu.mp.service.pojo.wxapp.order.Inquiry.InquiryToPayParam;
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
    @PostMapping("/api/wxapp/inquiry/order/detail")
    public JsonResult payOrder(@RequestBody InquiryOrderOnParam inquiryOrderOnParam){
        InquiryOrderDo inquiryOrderDo= shop().inquiryOrderService.getByOrderId(inquiryOrderOnParam.getOrderId());
        return success(inquiryOrderDo);
    }
                               /**
                                * 获取待问诊的订单
                                */
    @PostMapping("/api/wxapp/inquiry/order/list")
    public JsonResult refundOrder(@RequestBody @Validated InquiryOrderListParam param){
        PageResult<InquiryOrderDo> list=shop().inquiryOrderService.getInquiryOrderList(param);
        return success(list);
    }



}
