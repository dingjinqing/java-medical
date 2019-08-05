package com.vpu.mp.service.pojo.shop.market.groupdraw;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 拼团抽奖列表出参
 *
 * @author 郑保乐
 */
@Data
public class GroupDrawListVo {

    /** 进行中 **/
    public static final byte ONGOING = 0;
    /** 已过期 **/
    public static final byte FINISHED = 1;
    /** 已停用 **/
    public static final byte DISABLED = 2;
    /** 未开始 **/
    public static final byte NOT_STARTED = 3;

    private Integer id;
    /** 活动名称 **/
    private String name;
    /** 活动开始时间 **/
    private Timestamp startTime;
    /** 活动结束时间 **/
    private Timestamp endTime;
    /** 最小开奖人数 **/
    private Integer minJoinNum;
    /** 参团限制 **/
    private Integer joinLimit;
    /** 开团限制 **/
    private Integer openLimit;
    /** 最小成团人数 **/
    private Integer limitAmount;
    /** 参与用户达到多少前端展示 **/
    private Integer toNumShow;
    /** 1：启用，0：禁用 **/
    private Byte status;
    /** 是否已开奖 **/
    private Boolean isDraw;
    @JsonIgnore
    private String goodsId;

    /** 成团人数 **/
    private Integer groupUserCount;
    /** 开团数 **/
    private Integer groupCount;
    /** 中奖人数 **/
    private Integer drawUserCount;
    /** 商品数量 **/
    private Integer goodsCount;
    /** 参与人数 **/
    private Integer joinUserCount;
}
