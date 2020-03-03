package com.vpu.mp.service.pojo.wxapp.coupon.pack;

import com.vpu.mp.db.shop.tables.records.InvoiceRecord;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author: 王兵兵
 * @create: 2020-03-02 15:43
 **/
@Setter
@Getter
public class CouponPackOrderParam {
    /**
     * 优惠券包主键
     */
    private Integer packId;

    /**
     * 订单金额
     */
    private BigDecimal orderAmount;
    /**
     * 使用积分
     */
    private Integer scoreDiscount;
    /**
     * 使用余额
     */
    private BigDecimal accountDiscount;
    /**
     * 使用会员卡余额
     */
    private BigDecimal memberCardBalance;
    /**
     * 使用会员卡的卡号
     */
    private String cardNo;
    /**
     * 发票 TODO
     */
    private InvoiceRecord invoice;
}
