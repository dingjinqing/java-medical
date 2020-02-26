package com.vpu.mp.service.pojo.shop.market.seckill;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2020-02-26 21:00
 *
 **/
@Setter
@Getter
public class SeckillDecoratePageListVo {
    /**主键*/
    private Integer skId;
    /** 活动名称 */
    private String name;

    private Integer goodsId;
    private String goodsName;
    private String goodsImg;
    /**
     * 是否在售，1在售，0下架
     */
    private Byte isOnSale;
    /**
     * 最小的一个秒杀价格
     */
    private BigDecimal secPrice;
    /**
     * 活动库存
     */
    private Integer stock;

    private Timestamp startTime;
    private Timestamp endTime;
}
