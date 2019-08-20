package com.vpu.mp.service.pojo.shop.market.sharereward;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author liufei
 * @date 2019/8/19
 * @description
 */
@Data
public class ShareRewardShowParam {
    /** 分享有礼活动分页展示分模块，进行中8 ，未开始4，已过期2，已停用1，所有0 ；筛选优先级高于下面的条件*/
    private Byte category;
    /** 活动名称 */
    private String name;
    /** 活动起始时间 */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd hh:mm:ss")
    private Timestamp startTime;
    /** 活动结束时间 */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd hh:mm:ss")
    private Timestamp endTime;

    /** 以下字段为预留字段 */
    private BigDecimal fullPriceUp;
    private BigDecimal fullPriceDown;
    private BigDecimal purchasePriceUp;
    private BigDecimal purchasePriceDown;
    /** 状态 1: 启用 0:禁用' */
    private Byte status;

    private Integer currentPage;
    private Integer pageRows;
}
