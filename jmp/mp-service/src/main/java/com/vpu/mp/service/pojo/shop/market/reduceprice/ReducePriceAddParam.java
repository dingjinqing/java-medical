package com.vpu.mp.service.pojo.shop.market.reduceprice;

import com.vpu.mp.service.pojo.shop.config.ShopShareConfig;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2019-08-13 16:39
 **/
@Data
public class ReducePriceAddParam {

    /** 活动名称 */
    @NotNull
    private String name;

    /** 活动开始时间 */
    @NotNull
    private Timestamp startTime;

    /** 活动结束时间 */
    @NotNull
    private Timestamp endTime;

    /** 限购数量 */
    @NotNull
    private Integer limitAmount;

    /** 改价的商品数组 */
    @NotNull
    @Size(min=1)
    private ReducePriceGoodsAddParam[] reducePriceGoodsAddParams;

    /** 周期类型：1:每天 2:每月 3:每周 */
    private Byte periodAction;

    /** 时间段字符串 格式：09:00@12:00 */
    private String pointTime;

    /** 每月第几日；每周第几天 */
    private Byte extendTime;

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
