package com.vpu.mp.service.pojo.wxapp.market.bargain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 装修砍价模块BO
 * @author: 王兵兵
 * @create: 2019-12-18 18:04
 **/
@Getter
@Setter
public class BargainGoodsBo {

    @JsonProperty("goods_id")
    private Integer goodsId;
    @JsonProperty("act_id")
    private Integer actId;
    @JsonProperty("goods_name")
    private String goodsName;
    @JsonProperty("goods_img")
    private String goodsImg;
    @JsonProperty("goods_price")
    private BigDecimal goodsPrice;
    @JsonProperty("expectation_price")
    private BigDecimal expectationPrice;
    @JsonProperty("bargain_num")
    private Integer bargainNum;

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
    @JsonProperty("bargain_price")
    private BigDecimal bargainPrice;
    @JsonProperty("is_prd")
    private Byte isPrd;
    @JsonProperty("prd_id")
    private Integer prdId;
    @JsonProperty("max_price")
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
}
