package com.vpu.mp.service.pojo.shop.config.trade;

import lombok.Data;

/**
 * @Author:liufei
 * @Date:2019/7/8
 * @Description:
 * true：开启
 * false：未开启
 */
@Data
public class PaymentConfigParam {
    /** 支付方式-微信支付 */
    private Byte wxpay;
    /** 支付方式-积分支付 */
    private Byte score;
    /** 支付方式-余额支付 */
    private Byte balance;
    /** 支付方式-货到付款 */
    private Byte cod;
    /** 支付方式-存储卡支付 */
    private Byte membercard;
    /** 支付方式-支付宝支付 */
//    private boolean aliMiniPay;
}
