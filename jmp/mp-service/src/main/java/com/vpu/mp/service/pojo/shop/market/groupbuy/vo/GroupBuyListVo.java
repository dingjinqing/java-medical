package com.vpu.mp.service.pojo.shop.market.groupbuy.vo;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 孔德成
 * @date 2019/7/22 14:59
 */
@Data
@NoArgsConstructor
public class GroupBuyListVo {

    private Integer id;

    /**
     * 活动名称
     */
    private String name;

    /**
     * 活动类型：1：普通拼团，2：老带新团
     */
    private Byte activityType;

    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 优先级
     */
    private Integer level;

    /**
     * 有效期
     */
    private Timestamp startTime;
    private Timestamp endTime;

    /**
     * 总库存
     */
    private Short stock;
    /**
     * 销量
     */
    private Short saleNum;
    /**
     * 状态： 1：启用  0： 禁用
     */
    private Byte status;

    /**
     * 成团人数
     */
    private Short limitAmount;

    /**
     * 组团订单人数
     */
    private Integer groupOrderNum;

    /**
     * 当前活动状态：1进行中，2未开始，3已结束，4已停用
     */
    private Byte currentState;

}
