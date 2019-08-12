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

    /** 新用户 **/
    public static final byte BRAND_NEW = 1;
    /** 全部用户 **/
    public static final byte ALL = 2;
    /** 未支付过的用户 **/
    public static final byte NOT_PAYED = 3;

    /** 送券 **/
    public static final byte VOUCHER = 1;
    /** 幸运大抽奖 **/
    public static final byte DRAW = 2;
    /** 自定义活动 **/
    public static final byte CUSTOMIZE = 3;

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
