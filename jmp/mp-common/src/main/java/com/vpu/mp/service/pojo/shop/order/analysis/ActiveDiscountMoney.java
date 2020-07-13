package com.vpu.mp.service.pojo.shop.order.analysis;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.Data;

/**
 * 拼团报表 计算销售额bo类
 * @author 孔德成
 * @date 2019/8/5 10:07
 */
@Data
public class ActiveDiscountMoney {

    public static final String DISCOUNT_AMOUNT ="discountAmount";
    public static final String CREATE_TIME ="createTime";
    public static final String PAYMENT_AMOUNT ="paymentAmount";
    public static final String PAID_ORDER_NUMBER ="paidOrderNumber";
    public static final String PAID_GOODS_NUMBER ="paidGoodsNumber";

    /**
     * 时间 %Y-%m-%d
      */
    private Timestamp createTime;
    /**
     *  活动优惠总金额
      */
    private BigDecimal discountAmount;
    /**
     *  活动实付总金额
     */
    private BigDecimal paymentAmount;

    /** 付款订单数  */
    private Integer paidOrderNumber;
    /**
     *  商品数量
     */
    private Integer paidGoodsNumber;

}
