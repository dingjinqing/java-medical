package com.vpu.mp.service.pojo.shop.order.analysis;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 活动订单用户数量
 * @author 孔德成
 * @date 2019/8/2 17:24
 */
@Data
public class OrderActivityUserNum {

    private Timestamp date;

    private Integer num;
}
