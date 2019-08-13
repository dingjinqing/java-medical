package com.vpu.mp.service.pojo.shop.market.activity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * 添加活动入参
 *
 * @author 郑保乐
 */
@Data
public class ActivityParam {

    /* 触发条件 */
    /** 新用户 **/
    public static final byte BRAND_NEW = 1;
    /** 全部用户 **/
    public static final byte ALL = 2;
    /** 未支付过的用户 **/
    public static final byte NOT_PAYED = 3;

    private Integer id;
    /** 活动类型 **/
    @NotNull
    private Byte type;
    /** 名称 **/
    @Length(max = 20)
    @NotEmpty
    private String name;
    /** 宣传语 **/
    @Length(max = 20)
    private String title;
    /** 触发条件 **/
    @NotNull
    private Byte action;
    /** 背景图 **/
    private Byte bgAction;
    /** 开始时间 **/
    @NotNull
    private Timestamp startDate;
    /** 结束时间 **/
    @NotNull
    private Timestamp endDate;
    /** 优惠券id **/
    private List<Integer> couponId;
    /** 抽奖活动id **/
    private Integer activityId;
    /** 活动封面图片 **/
    private String customizeImgUrl;
    /** 自定义链接 **/
    private String customizePagePath;
}
