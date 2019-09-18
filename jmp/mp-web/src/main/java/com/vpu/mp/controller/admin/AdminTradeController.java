package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.config.trade.*;
import lombok.extern.slf4j.Slf4j;
import org.jooq.lambda.tuple.Tuple2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.math.NumberUtils.BYTE_ONE;
import static org.apache.commons.lang3.math.NumberUtils.BYTE_ZERO;

/**
 * @author liufei
 * @date 2019/7/8
 */
@Slf4j
@RestController
public class AdminTradeController extends AdminBaseController {

    /**
     * 支付方式开关配置
     *
     * @param paymentConfigParam 支付方式及对应的配置项
     */
    @PostMapping("/api/admin/config/trade/enablePayment")
    public JsonResult enablePayment(@RequestBody @Validated PaymentConfigParam paymentConfigParam) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(paymentConfigParam.getClass());
            PropertyDescriptor[] descriptor = beanInfo.getPropertyDescriptors();
            //更新支付配置开关
            Arrays.asList(descriptor).forEach((e) -> invoke(e, paymentConfigParam));
            //更新默认支付配置
            shop().trade.updateDefaultPayConf(paymentConfigParam);
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return success();
    }

    /**
     * 循环更新每一种支付方式的开启状态
     */
    private void invoke(PropertyDescriptor descriptor, PaymentConfigParam paymentConfigParam) {
        try {
            String paycode = descriptor.getName();
            Object enabled = descriptor.getReadMethod().invoke(paymentConfigParam);
            if (enabled != null) {
                log.debug("支付方式：{}，开关状态：{}", paycode, enabled.toString());
                Byte payStatus = "1".equals(enabled.toString()) ? BYTE_ONE : BYTE_ZERO;
                shop().trade.updatePayment(paycode, payStatus);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询支付方式开关配置
     */
    @PostMapping("/api/admin/config/trade/getPaymentEnabled")
    public JsonResult getPaymentEnabled() {
        List<PaymentConfigVo> payStatusList = shop().trade.getPaymentEnabled();
        Map<String, Byte> defaultPayConf = shop().trade.getDefaultPayConf();
        return success(new Tuple2<>(payStatusList, defaultPayConf));
    }

    /**
     * 微信支付密钥配置
     *
     * @param wxpayConfigParam 支付密钥相关信息
     */
    @PostMapping("/api/admin/config/trade/wxpayConfig")
    public JsonResult wxpayConfig(@RequestBody @Validated WxpayConfigParam wxpayConfigParam) {
        if (!saas.shop.mp.checkAuthShopExist(wxpayConfigParam.getAppId())) {
            return fail(JsonResultMessage.AUTH_SHOP_NOT_EXIST);
        }

        return saas.shop.mp.udpateWxpayConfig(wxpayConfigParam) > 0 ? success() : fail(JsonResultMessage.WECAHT_PAY_CONFIG_UPDATE_DAILED);
    }

    /**
     * 查询微信支付密钥配置
     */
    @PostMapping("/api/admin/config/trade/getWxpayConfig")
    public JsonResult getWxpayConfig(@RequestBody @Validated WxpaySearchParam wxpaySearchParam) {
        WxpayConfigParam wxpayConfigParam = null;
        if (saas.shop.mp.checkAuthShopExist(wxpaySearchParam.getAppId())) {
            wxpayConfigParam = saas.shop.mp.getWxpayConfig(wxpaySearchParam);
            return success(wxpayConfigParam);
        } else {
            return success(wxpayConfigParam);
        }
    }

    /**
     * 订单流程配置更新
     *
     * @param orderProcessParam 订单流程配置项信息
     */
    @PostMapping("/api/admin/config/trade/orderProcess")
    public JsonResult orderProcess(@RequestBody @Validated OrderProcessParam orderProcessParam) {
        shop().trade.updateOrderProcess(orderProcessParam);
        return success();
    }

    /**
     * 查询订单流程配置
     */
    @PostMapping("/api/admin/config/trade/getOrderProcessConfig")
    public JsonResult getOrderProcessConfig() {
        OrderProcessParam param = shop().trade.getOrderProcessConfig();
        return param != null ? success(param) : fail(JsonResultMessage.ORDER_PROCESS_CONFIG_IS_NULL);
    }

    /**
     * 退换货配置更新
     *
     * @param retrunConfigParam 退换货配置型信息
     */
    @PostMapping("/api/admin/config/trade/returnConfig")
    public JsonResult returnConfig(@RequestBody @Validated RetrunConfigParam retrunConfigParam) {
        return shop().config.returnConfigService.updateReturnConfig(retrunConfigParam) ? success() : fail(JsonResultMessage.RETURN_CONFIG_UPDATE_FAILED);
    }

    /**
     * 查询退换货配置
     */
    @PostMapping("/api/admin/config/trade/getReturnConfig")
    public JsonResult getReturnConfig() {
        RetrunConfigParam param = shop().config.returnConfigService.getRetrunConfigParam();
        return param != null ? success(param) : fail(JsonResultMessage.RETURN_CONFIG_IS_NULL);
    }

    /**
     * 查询退换货配置-商家默认收货地址配置信息
     */
    @PostMapping("/api/admin/config/trade/getdefaultaddress")
    public JsonResult getDefaultAddress() {
        return success(shop().config.returnConfigService.getDefaultAddress());
    }

}
