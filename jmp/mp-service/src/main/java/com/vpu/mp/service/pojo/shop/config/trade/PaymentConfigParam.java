package com.vpu.mp.service.pojo.shop.config.trade;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * The type Payment config param.
 *
 * @author liufei
 * @date 2019 /7/8
 */
@Data
public class PaymentConfigParam {
    /**
     * 支付方式-payment记录表
     * 1：开启状态 0：未开启状态
     * 微信支付
     */
    private Byte wxpay;
    /**
     * 积分支付
     */
    private Byte score;
    /**
     * 余额支付
     */
    private Byte balance;
    /**
     * 货到付款
     */
    private Byte cod;
    /**
     * 存储卡支付
     */
    private Byte membercard;

    /**
     * 默认支付配置-shopcfg表
     * 会员卡余额支付
     */
    @JsonProperty(value = "card_first")
    public Byte cardFirst;
    /**
     * The Balance first.余额支付
     */
    @JsonProperty(value = "balance_first")
    public Byte balanceFirst;
    /**
     * The Score first.积分支付
     */
    @JsonProperty(value = "score_first")
    public Byte scoreFirst;
}
