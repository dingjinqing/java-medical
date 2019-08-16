package com.vpu.mp.service.pojo.shop.market.firstspecial;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;

import lombok.Data;

/**
 * @author: 王兵兵
 * @create: 2019-08-16 16:39
 **/
@Data
public class FirstSpecialAddParam {

    /** 活动名称 */
    @NotNull
    private String name;

    /** 活动开始时间 */
    @NotNull
    private Timestamp startTime;

    /** 活动结束时间 */
    @NotNull
    private Timestamp endTime;

    /** 是否永久有效 */
    @NotNull
    private Byte isForever;

    /** 活动优先级 */
    @NotNull
    private Byte first;

    /** 限购数量 */
    @NotNull
    private Integer limitAmount;

    /** 改价的商品数组 */
    @NotNull
    @Size(min=1)
    private FirstSpecialGoodsParam[] firstSpecialGoodsParams;

    /** 批量打几折 */
    private Byte batchDiscount;

    /** 批量减多少 */
    private BigDecimal batchReduce;

    /** 批量折后价 */
    private BigDecimal batchFinalPrice;

    /** 是否批量取整 */
    private Byte isBatchInteger;

    /** 分享设置 */
    @NotNull
    private ShopShareConfig shareConfig;
}
