package com.vpu.mp.service.pojo.shop.config.trade;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:liufei
 * @Date:2019/7/8
 * @Description:
 * true：开启
 * false：未开启
 */
@Data
@NoArgsConstructor
public class PaymentConfigIn {
    /** 支付方式-微信支付 */
    private boolean wxpay;
    /** 支付方式-积分支付 */
    private boolean score;
    /** 支付方式-余额支付 */
    private boolean balance;
    /** 支付方式-货到付款 */
    private boolean cod;
    /** 支付方式-存储卡支付 */
    private boolean membercard;
    /** 支付方式-支付宝支付 */
//    private boolean aliMiniPay;
}
