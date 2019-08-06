package com.vpu.mp.controller.admin;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.config.trade.OrderProcessParam;
import com.vpu.mp.service.pojo.shop.config.trade.PaymentConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.PaymentConfigVo;
import com.vpu.mp.service.pojo.shop.config.trade.RetrunConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.WxpayConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.WxpaySearchParam;

/**
 * @Author:liufei
 * @Date:2019/7/8
 * @Description:
 */
@RestController
public class AdminTradeController extends AdminBaseController {

    /**
     * 支付方式开关配置
     * @param paymentConfigParam
     * @return
     */
    @PostMapping("/api/admin/config/trade/enablePayment")
    public JsonResult enablePayment(@RequestBody @Validated PaymentConfigParam paymentConfigParam){
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(paymentConfigParam.getClass());
            PropertyDescriptor[] descriptor = beanInfo.getPropertyDescriptors();
            Arrays.asList(descriptor).forEach((e)->invoke(e, paymentConfigParam));
        }catch(IntrospectionException e){
            e.printStackTrace();
        }
        return success();
    }

    /**
     * 循环更新每一种支付方式的开启状态
     * @param descriptor
     * @param paymentConfigParam
     */
    public void invoke(PropertyDescriptor descriptor,PaymentConfigParam paymentConfigParam){
        try{
            String paycode = descriptor.getName();
            Object enabled = descriptor.getReadMethod().invoke(paymentConfigParam);
            if(enabled!=null){
                shop().trade.updatePayment(paycode,(byte)enabled);
            }
        }catch(IllegalAccessException | InvocationTargetException e){
            e.printStackTrace();
        }
    }

    /**
     * 查询支付方式开关配置
     * @return
     */
    @PostMapping("/api/admin/config/trade/getPaymentEnabled")
    public JsonResult getPaymentEnabled(){
        List<PaymentConfigVo> list = shop().trade.getPaymentEnabled();
        return (list != null && !list.isEmpty()) ? success(list) : fail(JsonResultMessage.PAYMENT_CONFIG_IS_NULL);
    }

    /**
     * 微信支付密钥配置
     * @param wxpayConfigParam
     * @return
     */
    @PostMapping("/api/admin/config/trade/wxpayConfig")
    public JsonResult wxpayConfig(@RequestBody @Validated WxpayConfigParam wxpayConfigParam){
        if(!saas.shop.mp.checkAuthShopExist(wxpayConfigParam.getAppId())){
            return fail(JsonResultMessage.AUTH_SHOP_NOT_EXIST);
        }

        return saas.shop.mp.udpateWxpayConfig(wxpayConfigParam) > 0 ? success() : fail(JsonResultMessage.WECAHT_PAY_CONFIG_UPDATE_DAILED);
    }

    /**
     * 查询微信支付密钥配置
     * @return
     */
    @PostMapping("/api/admin/config/trade/getWxpayConfig")
    public JsonResult getWxpayConfig(@RequestBody @Validated WxpaySearchParam wxpaySearchParam){
        WxpayConfigParam wxpayConfigParam = null;
        if(saas.shop.mp.checkAuthShopExist(wxpaySearchParam.getAppId())){
            wxpayConfigParam = saas.shop.mp.getWxpayConfig(wxpaySearchParam);
            return success(wxpayConfigParam);
        }else{
            return success(wxpayConfigParam);
        }
    }

    /**
     * 订单流程配置更新
     * @param orderProcessParam
     * @return
     */
    @PostMapping("/api/admin/config/trade/orderProcess")
    public JsonResult orderProcess(@RequestBody @Validated OrderProcessParam orderProcessParam){
        return shop().trade.updateOrderProcess(orderProcessParam) ? success() : fail(JsonResultMessage.ORDER_PROCESS_CONFIG_UDPATE_FAILED);
    }

    /**
     * 查询订单流程配置
     * @return
     */
    @PostMapping("/api/admin/config/trade/getOrderProcessConfig")
    public JsonResult getOrderProcessConfig(){
        OrderProcessParam param = shop().trade.getOrderProcessConfig();
        return param != null ? success(param) : fail(JsonResultMessage.ORDER_PROCESS_CONFIG_IS_NULL);
    }

    /**
     * 退换货配置更新
     * @param retrunConfigParam
     * @return
     */
    @PostMapping("/api/admin/config/trade/retrunConfig")
    public JsonResult retrunConfig(@RequestBody @Validated RetrunConfigParam retrunConfigParam) {
        return shop().config.returnConfigService.updateReturnConfig(retrunConfigParam) ? success() : fail(JsonResultMessage.RETURN_CONFIG_UPDATE_FAILED);
    }

    /**
     * 查询退换货配置
     * @return
     */
    @PostMapping("/api/admin/config/trade/getRetrunConfig")
    public JsonResult getRetrunConfig(){
        RetrunConfigParam param = shop().config.returnConfigService.getRetrunConfigParam();
        return param != null ? success(param) : fail(JsonResultMessage.RETURN_CONFIG_IS_NULL);
    }
}
