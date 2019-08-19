package com.vpu.mp.service.pojo.shop.market.gift;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * 赠品策略入参
 *
 * @author 郑保乐
 */
@Data
public class RuleParam {

    /** 满金额 **/
    private Double fullPrice;
    /** 满件数 **/
    private Integer fullNumber;
    /** 持卡 **/
    private Integer cardId;
    /** 具备标签 **/
    private Integer tagId;
    /** 支付开始时间 **/
    private Timestamp payStartTime;
    /** 支付结束时间 **/
    private Timestamp payEndTime;
    /** 支付排名 **/
    private Integer payTop;
    /** 最少下单次数 **/
    private Integer minPayNum;
    /** 最多下单次数 **/
    private Integer maxPayNum;

    /** 规则（对）集合，开闭原则 **/
    @JsonIgnore
    private transient List<List<Supplier<?>>> rules = Arrays.asList(
        Collections.singletonList(this::getFullPrice),
        Collections.singletonList(this::getFullNumber),
        Collections.singletonList(this::getCardId),
        Collections.singletonList(this::getTagId),
        Collections.singletonList(this::getPayTop),
        Arrays.asList(this::getMinPayNum, this::getMaxPayNum),
        Arrays.asList(this::getPayStartTime, this::getPayEndTime)
    );
}
