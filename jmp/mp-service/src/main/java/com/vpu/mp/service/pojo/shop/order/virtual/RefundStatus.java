package com.vpu.mp.service.pojo.shop.order.virtual;

/**
 * @author huangronggang
 * @date 2019年8月5日
 */
public class RefundStatus {
    /** 未退款 */
    public static final byte NOT_REFUND = 0;
    /** 成功退款 */
    public static final byte SUCCESS = 1;
    /** 退款失败 */
    public static final byte FAILED = 2;
}

