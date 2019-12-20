package com.vpu.mp.service.pojo.wxapp.market.seckill;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author: 王兵兵
 * @create: 2019-12-19 14:52
 **/
@Setter
@Getter
public class SeckillGoodsBo {
    @JsonProperty("goods_id")
    private Integer goodsId;
    @JsonProperty("act_id")
    private Integer actId;
    @JsonProperty("sec_price")
    private BigDecimal secPrice;
    @JsonProperty("sale_num")
    private Integer saleNum;
    @JsonProperty("seckill_num")
    private Integer seckillNum;
    @JsonProperty("seckill_sale_num")
    private Integer seckillSaleNum;
    @JsonProperty("goods_name")
    private String goodsName;
    @JsonProperty("goods_img")
    private String goodsImg;
    @JsonProperty("goods_price")
    private BigDecimal goodsPrice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    @JsonProperty("act_begin_time")
    private Timestamp actBeginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",  timezone="GMT+8")
    @JsonProperty("act_end_time")
    private Timestamp actEndTime;
    @JsonProperty("act_status")
    private Byte actStatus;
    @JsonProperty("is_on_sale")
    private Byte isOnSale;
    @JsonProperty("act_del_flag")
    private Byte actDelFlag;
    @JsonProperty("goods_is_delete")
    private Byte goodsIsDelete;
    @JsonProperty("unit")
    private String unit;
    @JsonProperty("goods_number")
    private Integer goodsNumber;
    @JsonProperty("is_prd")
    private Byte isPrd;
    @JsonProperty("prd_id")
    private Integer prdId;
    @JsonProperty("max_rice")
    private BigDecimal maxPrice;
    /***
     * 0未开始，1进行中，2已结束
     */
    @JsonProperty("time_state")
    private Byte timeState;
    /**
     * 距活动 开始或结束 的时间
     */
    @JsonProperty("remaining_time")
    private Long remainingTime;
    @JsonProperty("base_sale")
    private Integer baseSale;
    /**
     * 秒杀价格是否是整数,1是
     */
    @JsonProperty("sec_price_int")
    private Byte secPriceInt;
    @JsonProperty("unpaidGoodsNum")
    private Integer unpaidGoodsNum;
}
