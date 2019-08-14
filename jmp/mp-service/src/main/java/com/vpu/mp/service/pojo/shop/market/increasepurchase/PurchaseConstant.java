package com.vpu.mp.service.pojo.shop.market.increasepurchase;

import lombok.Data;

/**
 * @author liufei
 * @date 2019/8/14
 * @description
 */
@Data
public class PurchaseConstant {
    /** 加价购页面分页展示分模块，进行中8 ，未开始4，已过期2，已停用1，所有0 ；筛选优先级高于下面的条件*/
    /**
     * 所有0
     */
    public static final Byte PURCHASE_ALL = 0b0000;
    /**
     * 已停用1
     */
    public static final Byte PURCHASE_TERMINATED = 0b0001;
    /**
     * 已过期2
     */
    public static final Byte PURCHASE_EXPIRED = 0b0010;
    /**
     * 未开始4
     */
    public static final Byte PURCHASE_PREPARE = 0b0100;
    /**
     * 进行中8
     */
    public static final Byte PURCHASE_PROCESSING = 0b1000;
}
