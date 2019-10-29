package com.vpu.mp.service.pojo.shop.market;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 营销活动订单的行信息
 * @author: 王兵兵
 * @create: 2019-09-04 16:03
 **/
@Data
public class MarketOrderGoodsListVo {
    private Integer goodsId;
    private String goodsName;
    private String goodsImg;
    private BigDecimal goodsPrice;
}
