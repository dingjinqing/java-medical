package com.vpu.mp.service.pojo.shop.market.gift;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 赠品活动入参
 *
 * @author 郑保乐
 */
@Data
public class GiftListVo {

    private Integer id;
    /** 活动名称 **/
    private String name;
    /** 优先级 **/
    private Short level;
    /** 开始时间 **/
    private Timestamp startTime;
    /** 结束时间 **/
    private Timestamp endTime;
    /** 赠送次数 **/
    private Integer giftTimes;
    /** 活动状态 **/
    private Byte status;

    /* 活动状态 */
    /** 开启 **/
    public static final byte ABLE = 1;
    public static final byte DISABLE = 0;
}
