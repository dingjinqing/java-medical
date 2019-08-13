package com.vpu.mp.service.pojo.shop.market.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * 活动有礼列表出参
 *
 * @author 郑保乐
 */
@Data
public class ActivityVo {

    private Integer id;
    /** 活动名称 **/
    private String name;
    /** 宣传语 **/
    private String title;
    /** 背景图 **/
    private Byte bgAction;
    /** 触发条件 **/
    private Byte action;
    /** 活动类型 **/
    private Byte activityAction;
    private Timestamp startDate;
    private Timestamp endDate;
    /** 优惠券 **/
    private List<Integer> voucherId;

    @JsonIgnore
    private String mrkingVoucherId;
}
