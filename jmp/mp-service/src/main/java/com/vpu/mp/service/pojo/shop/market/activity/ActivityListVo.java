package com.vpu.mp.service.pojo.shop.market.activity;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 活动有礼列表出参
 *
 * @author 郑保乐
 */
@Data
public class ActivityListVo {

    private Integer id;
    /* 活动类型 */
    /** 送券 **/
    public static final byte COUPON = 0;
    /** 幸运大抽奖 **/
    public static final byte DRAW = 1;
    /** 自定义活动 **/
    public static final byte CUSTOMIZE = 2;

    /* 活动状态 */
    /** 开启 **/
    public static final byte ABLE = 1;
    public static final byte DISABLE = 0;

    /** 活动名称 **/
    private String name;
    /** 触发条件 **/
    private Byte action;
    /** 活动类型 **/
    private Byte activityAction;
    private Timestamp startDate;
    private Timestamp endDate;
    private Byte status;
}
