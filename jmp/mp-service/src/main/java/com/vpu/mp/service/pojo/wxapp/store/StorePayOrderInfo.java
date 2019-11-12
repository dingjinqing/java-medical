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

    /**
     * store_id : 55
     * card_no : 47481606403254132
     * order_amount : 0.01
     * card_dis : 0.01
     * invoice : 62
     * score_dis : 0
     * total_price : 0.00
     * openid : o-2MM5ANXgJHG_NBG5G-WX-KPjKI
     * form_id : the formId is a mock one
     */
    @PositiveOrZero
    public Integer storeId;
    /**
     * The Card no.
     */
    public String cardNo;
    /**
     * The Order amount.订单金额
     */
    public BigDecimal orderAmount;
    /**
     * The Card charge dis.会员卡抵扣金额
     */
    public BigDecimal cardChargeDis;
    /**
     * The Card dis.
     */
    public String cardDis;
    /**
     * The Invoice.发票
     */
    public int invoiceId;
    /**
     * The Score dis.积分抵扣金额
     */
    public BigDecimal scoreDis;
    /**
     * The Score dis.余额抵扣金额
     */
    public BigDecimal accountDis;
    /**
     * The Total price.
     */
    public BigDecimal totalPrice;
    /**
     * The Openid.
     */
    public String openid;
    /**
     * The Form id.
     */
    public String formId;
    /**
     * The remark
     */
    public String remark;
}
