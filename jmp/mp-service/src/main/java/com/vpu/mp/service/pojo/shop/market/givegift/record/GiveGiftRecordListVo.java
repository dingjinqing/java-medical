package com.vpu.mp.service.pojo.shop.market.givegift.record;

import lombok.Data;

import java.math.BigDecimal;
import java.nio.ByteOrder;
import java.sql.Timestamp;

/**
 * @author 孔德成
 * @date 2019/8/19 11:11
 */
@Data
public class GiveGiftRecordListVo {
    /**
     * 订单号
     */
    String mainOrderSn;
    /**
     * 支付金额
     */
    BigDecimal orderAmount;
    /**
     * 下单时间
     */
    Timestamp createTime;
    /**
     * 支付时间
     */
    Timestamp payTime;
    /**
     * 订单状态
     */
    Byte orderStatus;
    /**
     * 订单状态名称
     */
    String orderStatusName;
    /**
     * 礼单类型
     */
    Byte giftType;
    /**
     * 指定商品可用 id
     */
    String recommendGoodsId;
    /**
     * 送人ID
     */
    Integer userId;
    /**
     * 名称
     */
    String userName;
    /**
     * 电话
     */
    String mobile;
}
