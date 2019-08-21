package com.vpu.mp.service.pojo.shop.market.sharereward;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

/**
 * @author liufei
 * @date 2019/8/19
 * @description
 */
@Data
public class ShareRewardShowVo {
    /**
     * 活动id
     */
    private Integer id;
    /**
     * 活动名称
     */
    private String name;
    /**
     * 触发条件
     */
    private Byte condition;
    /**
     * 有效期，根据实际情况由活动起始时间，活动结束时间，是否永久有效三项组成
     */
    private String validityPeriod;
    /**
     * 活动奖励
     */
    private Set<Byte> rewardType;
    /**
     * 活动优先级
     */
    private Short priority;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 分享人数
     */
    private Integer shareNum;
    /**
     * 邀请人数
     */
    private Integer inviteNum;
    /**
     * 活动起始时间
     */
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp startTime;
    /**
     * 活动结束时间
     */
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Timestamp endTime;
    /**
     * 是否永久有效
     */
    @JsonIgnore
    private Byte isForever;
    /**
     * 一级分享规则
     */
    @JsonIgnore
    private String firstLevelRule;
    /**
     * 二级分享规则
     */
    @JsonIgnore
    private String secondLevelRule;
    /**
     * 三级分享规则
     */
    @JsonIgnore
    private String thirdLevelRule;

}
