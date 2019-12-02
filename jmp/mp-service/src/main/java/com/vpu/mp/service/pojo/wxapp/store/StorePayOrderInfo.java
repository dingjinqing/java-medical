package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

/**
 * The type Store pay order info.
 *
 * @author liufei
 * @date 10 /22/19
 */
@Data
public class StorePayOrderInfo {
    @PositiveOrZero(groups = {StoreConfirmPay.class})
    public Integer storeId;
    /**
     * The Card no.会员卡号
     */
    @NotBlank(groups = {StoreConfirmPay.class})
    public String cardNo;
    /**
     * The Order amount.订单金额
     */
    @Positive(groups = {StoreConfirmPay.class})
    @Digits(groups = {StoreConfirmPay.class}, integer = Integer.MAX_VALUE, fraction = 2)
    public BigDecimal orderAmount;
    /**
     * The Card charge dis.会员卡余额抵扣金额
     */
    @PositiveOrZero(groups = {StoreConfirmPay.class})
    @Digits(groups = {StoreConfirmPay.class}, integer = Integer.MAX_VALUE, fraction = 2)
    public BigDecimal cardAmount;
    /**
     * The Card dis.会员卡折扣抵扣金额
     */
    @PositiveOrZero(groups = {StoreConfirmPay.class})
    @Digits(groups = {StoreConfirmPay.class}, integer = Integer.MAX_VALUE, fraction = 2)
    public BigDecimal cardDisAmount;
    /**
     * The Invoice.发票
     */
    public int invoiceId;
    /**
     * The Score dis.积分抵扣金额
     */
    @PositiveOrZero(groups = {StoreConfirmPay.class})
    @Digits(groups = {StoreConfirmPay.class}, integer = Integer.MAX_VALUE, fraction = 2)
    public BigDecimal scoreAmount;
    /**
     * The Score dis.余额抵扣金额
     */
    @PositiveOrZero(groups = {StoreConfirmPay.class})
    @Digits(groups = {StoreConfirmPay.class}, integer = Integer.MAX_VALUE, fraction = 2)
    public BigDecimal balanceAmount;
    /**
     * The Total price.应付金额
     */
    @PositiveOrZero(groups = {StoreConfirmPay.class})
    @Digits(groups = {StoreConfirmPay.class}, integer = Integer.MAX_VALUE, fraction = 2)
    public BigDecimal moneyPaid;
    /**
     * The remark 留言
     */
    public String remark;
}
