package com.vpu.mp.service.pojo.shop.market.groupdraw;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 拼团抽奖列表出参
 *
 * @author 郑保乐
 */
@Data
public class GroupDrawAddParam {

    /** 活动名称 **/
    private String name;
    /** 活动开始时间 **/
    private Timestamp startTime;
    /** 活动结束时间 **/
    private Timestamp endTime;
    /** 奖池最少人数 **/
    private Short minJoinNum;
    /** 商品金额 **/
    private BigDecimal payMoney;
    /** 最大参团数量 **/
    private Short joinLimit;
    /** 最大开团数量 **/
    private Short openLimit;
    /** 最小成团人数 **/
    private Short limitAmount;
    /** 最小展示人数 **/
    private Short toNumShow;
    /** 鼓励奖优惠券 **/
    private List<Integer> rewardCouponIds;
    /** 商品 id **/
    private List<Integer> goodsIds;

    @JsonIgnore
    private String goodsId;
    @JsonIgnore
    private String rewardCouponId;
}
