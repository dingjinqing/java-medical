package com.vpu.mp.service.pojo.shop.recharge;

import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 赵晓东
 * @description 充值查询列表入参
 * @create 2020-07-27 16:22
 **/
@Data
public class RechargeParam {

    /**
     * 充值日期开始时间
     */
    private Timestamp startCreateTime;
    /**
     * 充值日期截止时间
     */
    private Timestamp endCreateTime;
    /**
     * 分页
     */
    private Integer page;
    private Integer rows;
}
