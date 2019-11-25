package com.vpu.mp.service.pojo.wxapp.pay.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.binarywang.wxpay.bean.result.WxPayUnifiedOrderResult;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author 王帅
 * 前台调用微信支付参数基类
 */
@Getter
@Setter
@ToString
public abstract class WebPayVo {
    /**统一下单实际返回值*/
    @JsonIgnore
    private WxPayUnifiedOrderResult result;
    private String orderSn;
    private String orderType;
}
