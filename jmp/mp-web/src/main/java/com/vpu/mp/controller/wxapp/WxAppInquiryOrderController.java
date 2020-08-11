package com.vpu.mp.controller.wxapp;

import com.vpu.mp.common.foundation.data.JsonResult;
import com.vpu.mp.common.foundation.data.JsonResultCode;
import com.vpu.mp.common.foundation.util.PageResult;
import com.vpu.mp.common.foundation.util.RequestUtil;
import com.vpu.mp.common.pojo.shop.table.InquiryOrderDo;
import com.vpu.mp.service.foundation.exception.MpException;
import com.vpu.mp.service.pojo.wxapp.login.WxAppSessionUser;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderListParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderOnParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryOrderParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.InquiryToPayParam;
import com.vpu.mp.service.pojo.wxapp.order.inquiry.vo.InquiryOrderDetailVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 问诊订单
 * @author yangpengcheng
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
        try {
             shop().inquiryOrderService.refund(inquiryOrderOnParam);
        } catch (MpException e) {
            fail(e.getErrorCode());
        }
        return success();
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
    public JsonResult orderDetail(@RequestBody InquiryOrderOnParam inquiryOrderOnParam){
        if(StringUtils.isBlank(inquiryOrderOnParam.getOrderSn())){
            return fail(JsonResultCode.INQUIRY_ORDER_SN_IS_NULL);
        }
        InquiryOrderDetailVo inquiryOrderDetailVo= shop().inquiryOrderService.getDetailByOrderSn(inquiryOrderOnParam.getOrderSn());
        return success(inquiryOrderDetailVo);
    }
    /**
     * 更改问诊订单状态
     */
    @PostMapping("/api/wxapp/inquiry/order/status/update")
    public JsonResult updateStatus(@RequestBody InquiryOrderOnParam inquiryOrderOnParam){
        shop().inquiryOrderService.updateOrder(inquiryOrderOnParam);
        return success();
    }

    /**
     * 查询未完成的问诊
     * @param param
     * @return
     */
    @PostMapping("/api/wxapp/inquiry/order/undone/get")
    public JsonResult getUndoneOrder(@RequestBody InquiryOrderParam param){
        return success(shop().inquiryOrderService.getUndoneOrder(param));
    }
    /**
     * 新增
     * @param inquiryOrderDo
     * @return
     */
    @PostMapping("/api/wxapp/inquiry/order/insert")
    public JsonResult insert(@RequestBody InquiryOrderDo inquiryOrderDo){
        shop().inquiryOrderService.insert(inquiryOrderDo);
        return success();
    }

}
