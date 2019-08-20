package com.vpu.mp.service.pojo.shop.market.gift;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 赠品策略入参
 *
 * @author 郑保乐
 */
@Data
public class RuleVo {

    /** 满金额 **/
    private Double fullPrice;
    /** 满件数 **/
    private Integer fullNumber;
    /** 持卡 **/
    private Integer cardId;
    /** 具备标签 **/
    private Integer tagId;
    /** 支付开始时间 **/
    private Timestamp payStartTime;
    /** 支付结束时间 **/
    private Timestamp payEndTime;
    /** 支付排名 **/
    private Integer payTop;
    /** 最少下单次数 **/
    private Integer minPayNum;
    /** 最多下单次数 **/
    private Integer maxPayNum;
}
