package com.vpu.mp.service.pojo.shop.market.seckill;

import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2019-08-06 13:54
 **/
@Data
public class SeckillAddParam {
    /** 活动名称*/
    @NotNull
    private String name;

    /** 商品id*/
    @NotNull
    @Min(1)
    private Integer goodsId;

    /** 开始时间*/
    @NotNull
    private Timestamp startTime;

    /** 结束时间*/
    @NotNull
    private Timestamp endTime;

    /** 总库存（各规格秒杀库存之和）*/
    @NotNull
    private Integer stock;

    /** 每人限购数量*/
    @NotNull
    @Min(0)
    private Short limitAmount;

    /** 规定的有效支付时间 单位：分钟*/
    @NotNull
    @Min(0)
    private Short limitPaytime;

    /** 秒杀商品规格价格设置实体*/
    @NotNull
    @Size(min=1)
    private SeckillProductAddParam[] secKillProduct;

    /** 是否免运费： 1：免运费  0： 原先商品的运费*/
    @NotNull
    @Min(0)
    @Max(1)
    private Byte freeFreight;

    /**活动初始销量*/
    private Integer baseSale;

    /** 专属会员卡，卡ID字符串，逗号分隔；为空时代表该活动所有人都可以参与*/
    private String cardId;

    /** 分享设置*/
    private ShopShareConfig shareConfig;
}
