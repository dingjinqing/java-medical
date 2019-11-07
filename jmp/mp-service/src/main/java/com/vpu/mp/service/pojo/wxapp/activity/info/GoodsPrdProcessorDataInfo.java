package com.vpu.mp.service.pojo.wxapp.activity.info;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * @author 李晓冰
 * @date 2019年11月04日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GoodsPrdProcessorDataInfo extends ProcessorDataInfo {
    /*******小程序商品列表********/
    /**是否是默认规格0否1是*/
    private Boolean defaultPrd;
    /**最小值*/
    private BigDecimal minPrice;
    /**最大值*/
    private BigDecimal maxPrice;

    /******商品详情处使用********/
    private Integer prdId;
    private Integer prdNumber;
    private BigDecimal prdPrice;
    private BigDecimal prdMarketPrice;
    private String prdSpecs;
    private String prdDesc;
    private String prdImg;
}
