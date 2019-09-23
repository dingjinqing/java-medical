package com.vpu.mp.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.foundation.data.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.config.trade.*;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenResult;
import org.jooq.lambda.tuple.Tuple2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.vpu.mp.service.pojo.shop.config.trade.TradeConstant.*;
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
        }
        return success(wxpayConfigParam);
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
        OrderProcessParam param = null;
        try {
            param = shop().trade.getOrderProcessConfig();
        } catch (WxErrorException e) {
            log.error("微信物流助手api调用失败，获取支持物流公司列表失败：{}", e.getMessage());
            return fail(JsonResultCode.CODE_FAIL);
        }
        return success(param);
    }

    /**
     * 退换货配置更新
     *
     * @param returnConfigParam 退换货配置型信息
     */
    @PostMapping("/api/admin/config/trade/returnConfig")
    public JsonResult returnConfig(@RequestBody @Validated ReturnConfigParam returnConfigParam) {
        shop().config.returnConfigService.updateReturnConfig(returnConfigParam);
        return success();
    }

    /**
     * 查询退换货配置
     */
    @PostMapping("/api/admin/config/trade/getReturnConfig")
    public JsonResult getReturnConfig() {
        return success(shop().config.returnConfigService.getReturnConfigParam());
    }

    /**
     * 查询退换货配置-商家默认收货地址配置信息
     */
    @PostMapping("/api/admin/config/trade/getdefaultaddress")
    public JsonResult getDefaultAddress() {
        return success(shop().config.returnConfigService.getDefaultAddress());
    }

    /**
     * 服务条款配置
     *
     * @param serviceDocument 服务条款配置内容
     */
    @RequestMapping("/api/admin/config/trade/conftermsofservice")
    public JsonResult confTermsOfService(@RequestParam("service_document") String serviceDocument) {
        try {
            shop().trade.confTermsOfService(serviceDocument);
            return success();
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return fail(JsonResultCode.CODE_CONFIG_UPDATE_FAILED, e.getMessage());
        }
    }

    /**
     * 查询服务条款配置
     *
     * @return 服务条款配置内容
     */
    @RequestMapping("/api/admin/config/trade/gettermsofservice")
    public JsonResult getTermsOfService() {
        try {
            return success(shop().trade.getTermsOfService());
        } catch (IOException e) {
            log.error(e.getMessage());
            return fail();
        }
    }

    /**
     * 微信物流助手-绑定物流公司
     */
    @RequestMapping("/api/admin/config/trade/bindaccount")
    public JsonResult bindAccount(@RequestBody @Validated BindAccountParam param) {
        try {
            WxOpenResult result = shop().trade.bindAccount(param);
            switch (result.getErrcode()) {
                case WXERROR_10000001:
                    return fail(JsonResultCode.CODE_FAIL);
                case WXERROR_9300529:
                    return fail(JsonResultCode.WX_9300529);
                case WXERROR_9300530:
                    return fail(JsonResultCode.WX_9300530);
                case WXERROR_9300531:
                    return fail(JsonResultCode.WX_9300531);
                case WXERROR_9300532:
                    return fail(JsonResultCode.WX_9300532);
                default:
                    return success();
            }
        } catch (WxErrorException e) {
            log.error("微信api logistics.bindAccount 调用失败：{}", e.getMessage());
            return fail(JsonResultCode.CODE_FAIL);
        }
    }
}
