package com.vpu.mp.service.pojo.wxapp.store;

import lombok.Data;

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
    public String cardNo;
    /**
     * The Order amount.订单金额
     */
    public BigDecimal orderAmount;
    /**
     * The Card charge dis.会员卡余额抵扣金额
     */
    public BigDecimal cardAmount;
    /**
     * The Card dis.会员卡折扣抵扣金额
     */
    public BigDecimal cardDisAmount;
    /**
     * The Invoice.发票
     */
    public int invoiceId;
    /**
     * The Score dis.积分抵扣金额
     */
    public BigDecimal scoreAmount;
    /**
     * The Score dis.余额抵扣金额
     */
    public BigDecimal balanceAmount;
    /**
     * The Total price.应付金额
     */
    public BigDecimal moneyPaid;
    /**
     * The remark 留言
     */
    public String remark;
}
