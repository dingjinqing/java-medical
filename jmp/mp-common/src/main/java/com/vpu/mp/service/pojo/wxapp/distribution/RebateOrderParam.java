package com.vpu.mp.service.pojo.wxapp.distribution;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author 常乐
 * @Date 2020-04-11
 */
@Data
public class RebateOrderParam {
    /**
     *  邀请人ID
     */
    private Integer userId;
    /**
     * 订单开始时间
     */
    private Timestamp startTime;
    /**
     * 订单结束时间
     */
    private Timestamp endTime;

    private Integer currentPage;

    private Integer rowsPage;
}
