package com.vpu.mp.service.pojo.shop.market.seckill;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author: 王兵兵
 * @create: 2019-08-06 17:06
 **/
@Data
public class SecKillProductVo {
    private Integer skproId;
    private String prdDesc;
    private BigDecimal prdPrice;
    private Integer prdNumber;
    private BigDecimal secKillPrice;
    private Short totalStock;
    private Short stock;
}
