package com.vpu.mp.controller.admin;

import com.vpu.mp.service.foundation.JsonResult;
import com.vpu.mp.service.foundation.JsonResultMessage;
import com.vpu.mp.service.pojo.shop.config.ShopCfg;
import com.vpu.mp.service.pojo.shop.config.trade.PaymentConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.RetrunConfigParam;
import com.vpu.mp.service.pojo.shop.config.trade.WxpayConfigParam;
import org.springframework.validation.BindingResult;
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

/**
 * @Author:liufei
 * @Date:2019/7/8
 * @Description:
 */
@RestController
public class AdminTradeController extends AdminBaseController {
    private final static Byte PAY_ENABLED = 1;
    private final static Byte PAY_UNENABLED = 0;

    /**
     * 支付方式开关配置
     * @param paymentConfigParam
     * @return
     */
    @PostMapping("/api/admin/config/trade/enablePayment")
    public JsonResult enablePayment(@RequestBody PaymentConfigParam paymentConfigParam){
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
            Object object = descriptor.getReadMethod().invoke(paymentConfigParam,null);
            if(object!=null){
                boolean boo = Boolean.parseBoolean(object.toString());
                byte enabled = boo ? PAY_ENABLED : PAY_UNENABLED;
                shop().trade.updatePayment(paycode,enabled);
            }
        }catch(IllegalAccessException e){
            e.printStackTrace();
        }catch(InvocationTargetException e){
            e.printStackTrace();
        }
    }

    /**
     * 微信支付密钥配置
     * @param wxpayConfigParam
     * @param result
     * @return
     */
    @PostMapping("/api/admin/config/trade/wxpayConfig")
    public JsonResult wxpayConfig(@RequestBody @Validated WxpayConfigParam wxpayConfigParam, BindingResult result){
        if (result.hasErrors()) {
            return this.fail(result.getFieldError().getDefaultMessage());
        }
        if(!saas.wechat.checkAuthShopExist(wxpayConfigParam.getAppId())){
            return fail(JsonResultMessage.AUTH_SHOP_NOT_EXIST);
        }

        return saas.wechat.udpateWxpayConfig(wxpayConfigParam) > 0 ? success() : fail(JsonResultMessage.WECAHT_PAY_CONFIG_UPDATE_DAILED);
    }

    /**
     * 订单流程配置更新
     * @param shopCfgList
     * @return
     */
    @PostMapping("/api/admin/config/trade/orderProcess")
    public JsonResult orderProcess(@RequestBody List<ShopCfg> shopCfgList){
        return shop().trade.updateOrderProcess(shopCfgList) > 0 ? success() : fail(JsonResultMessage.ORDER_PROCESS_CONFIG_UDPATE_FAILED);
    }

    /**
     * 退换货配置更新
     * @param retrunConfigParam
     * @return
     */
    @PostMapping("/api/admin/config/trade/retrunConfig")
    public JsonResult retrunConfig(@RequestBody RetrunConfigParam retrunConfigParam) {
        return shop().config.returnConfigService.updateReturnConfig(retrunConfigParam) ? success() : fail(JsonResultMessage.RETURN_CONFIG_UPDATE_FAILED);
    }
}
