package com.vpu.mp.service.pojo.shop.distribution;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author 常乐
 * @Date 2020-03-13
 */
@Data
public class RebateCenterVo {
    /**
     * 可提现佣金
     */
    private BigDecimal canWithdraw;
    /**
     * 累积获得佣金
     */
    private BigDecimal totalWithdraw;
    /**
     * 待返利佣金
     */
    private BigDecimal waitWithdraw;
    /**
     * 邀请码
     */
    private String invitationCode;
    /**
     * 邀请用户数
     */
    private Integer inviteUserNum;
    /**
     * 返利订单数
     */
    private Integer rebateOrderNum;
    /**
     * 累积返利商品总额
     */
    private BigDecimal TotalCanFanliMoney;
    /**
     * 返利佣金排名
     */
    private List<DistributorRankingVo> distributorRanking;
    /**
     * 我的等级
     */
    private String distributorLevel;
    /**
     * 我的分组
     */
    private String distributorGroup;
}
