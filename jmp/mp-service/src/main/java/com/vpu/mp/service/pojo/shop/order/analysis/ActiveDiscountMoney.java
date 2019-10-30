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
    public static final String MARKET_PRICE ="marketPrice";
    public static final String GOODS_PRICE ="goodsPrice";
    public static final String GOODS_NUMBER ="goodsNumber";
    public static final String DISCOUNTEDTOTALPRICE ="discountedTotalPrice";

    /**
     * 时间 %Y-%m-%d
      */
    private Timestamp createTime;
    /**
     *  活动优惠金额
      */
    private BigDecimal marketPrice;
    /**
     *  活动总金额
     */
    private BigDecimal goodsPrice;
    /**
     *  商品数量
     */
    private int goodsNumber;
    /**
     *  活动实付总金额
     */
    private BigDecimal discountedTotalPrice;

    private String old;
}
