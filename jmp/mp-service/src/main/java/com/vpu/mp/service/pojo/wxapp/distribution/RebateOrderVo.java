package com.vpu.mp.service.pojo.wxapp.distribution;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @Author 常乐
 * @Date 2020-04-11
 */
@Data
public class RebateOrderVo {
    /**
     * 订单编号
     */
    private String orderSn;
    /**
     * 下单时间
     */
    private Timestamp createTime;
    /**
     * 订单完成时间
     */
    private Timestamp finishedTime;
    /**
     * 下单用户
     */
    private String username;
    /**
     * 订单状态编码
     */
    private Integer orderStatus;
    /**
     * 订单状态名称
     */
    private String orderStatusName;
    /**
     * 订单返利总金额
     */
    private BigDecimal fanliMoney;
}
