package com.vpu.mp.service.pojo.shop.sku.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2020年07月02日
 */
@Data
public class GoodsSpecProduct {
    private Integer prdId;
    private Integer goodsId;
    private String prdSn;
    private BigDecimal prdPrice;
    private BigDecimal prdMarketPrice;
    private BigDecimal prdCostPrice;
    private Integer prdNumber;
    private String prdCodes;
    private String prdSpecs;
    private String prdDesc;
    private String prdImg;
    private BigDecimal prdWeight;
    private Byte delFlag;
    private Timestamp createTime;
}
