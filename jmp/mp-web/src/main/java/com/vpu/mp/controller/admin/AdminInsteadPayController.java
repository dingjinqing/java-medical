package com.vpu.mp.controller.admin;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vpu.mp.service.foundation.data.JsonResult;
import com.vpu.mp.service.foundation.data.JsonResultCode;
import com.vpu.mp.service.pojo.shop.market.insteadpay.InsteadPay;

/**
 * @author 李晓冰
 * @date 2019年08月19日
 */
@RestController
public class AdminInsteadPayController extends AdminBaseController {

    /**
     * 设置好友代付
     *
     * @param param 配置内容
     * @return 配置结果
     */
    @RequestMapping("/api/admin/market/instead/pay/set")
    public JsonResult setInsteadPayConfig(@RequestBody InsteadPay param) {
        int messageLength = 20;
        int ratioLen=3;
        int ratioNumLen=2;

        if (param.getStatus() == null) {
            return fail(JsonResultCode.INSTEAD_PAY_STATUS_IS_NULL);
        }

        //可以代付,进行参数检查
        if (param.getStatus()) {

            //未设置代付类型
            if (param.getSinglePay() == null && param.getMultiplePay() == null) {
                return fail(JsonResultCode.INSTEAD_PAY_NOT_SET_PAY_WAY);
            }
            if (param.getSinglePay() == null && !param.getMultiplePay()) {
                return fail(JsonResultCode.INSTEAD_PAY_NOT_SET_PAY_WAY);
            }
            if (param.getMultiplePay() == null && !param.getSinglePay()){
                return fail(JsonResultCode.INSTEAD_PAY_NOT_SET_PAY_WAY);
            }


            //开启了单人代付
            if (param.getSinglePay()) {
                //未填写单人代付留言
                if (StringUtils.isBlank(param.getOrderUserMessageSingle()) ||
                    StringUtils.isBlank(param.getInsteadPayMessageSingle())) {
                    return fail(JsonResultCode.INSTEAD_PAY_NOT_SET_SINGLE_PAY_MESSAGE);
                }
                //单人留言长度超过20
                if (param.getOrderUserMessageSingle().length() > messageLength ||
                    param.getInsteadPayMessageSingle().length() > messageLength) {
                    return fail(JsonResultCode.INSTEAD_PAY_SINGLE_PAY_MESSAGE_TOO_LONG);
                }
            }

            //开启了多人代付
            if (param.getMultiplePay()) {
                //未填写多人代付留言
                if (StringUtils.isBlank(param.getOrderUserMessageMultiple()) ||
                    StringUtils.isBlank(param.getInsteadPayMessageMultiple())) {
                    return fail(JsonResultCode.INSTEAD_PAY_NOT_SET_MULTIPLE_PAY_MESSAGE);
                }
                //多人留言长度超过20
                if (param.getOrderUserMessageMultiple().length() > messageLength ||
                    param.getInsteadPayMessageMultiple().length() > messageLength) {
                    return fail(JsonResultCode.INSTEAD_PAY_MULTIPLE_PAY_MESSAGE_TOO_LONG);
                }
                //多人代付金额比需要3个
                if (param.getPayRatio() == null || param.getPayRatio().size() != ratioLen) {
                    return fail(JsonResultCode.INSTEAD_PAY_NEED_AT_LEAST_THREE_PAY_RATIO);
                }
                //检查用户设置的金额比
                int doubleNum = 0;
                for (Object value : param.getPayRatio().values()) {
                    //如果值为浮点数就表示设置的定额，为字符串表示剩余款项全部支付
                    if (value instanceof Double||value instanceof Integer) {
                        doubleNum++;
                        Double d = Double.parseDouble(value.toString());
                        //代付金额比例需要在0-100之间
                        if (d<=0 || d >=100) {
                            return fail(JsonResultCode.INSTEAD_PAY_VALUE_OVER_RANGE);
                        }
                    }
                }
                //最少设置两个浮点金额比
                if (doubleNum < ratioNumLen) {
                    return fail(JsonResultCode.INSTEAD_PAY_NEED_AT_LEAST_TWO_DOUBLE_PAY_RATIO);
                }
            }

            if (param.getSinglePay() && param.getMultiplePay()) {
                param.setInsteadPayWay(InsteadPay.TWO_WAY);
            } else {
                param.setInsteadPayWay(InsteadPay.ONE_WAY);
            }
        } else {
            param.setInsteadPayWay(InsteadPay.NOT_SET);
        }

        shop().config.insteadPayConfig.setInsteadPayConfig(param);
        return success();
    }

    /**
     * 获取好友代付配置项
     *
     * @return 代付配置
     */
    @RequestMapping("/api/admin/market/instead/pay/get")
    public JsonResult getInsteadPayConfig() {
        InsteadPay insteadPayConfig = shop().config.insteadPayConfig.getInsteadPayConfig();
        return success(insteadPayConfig);
    }
}
