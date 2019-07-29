package com.vpu.mp.service.pojo.shop.market.groupbuy;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 孔德成
 * @date 2019/7/19 10:50
 */
@Data
public class GroupBuyProductParam {


    /**
     * 拼团定义Id
     */
    private Integer  activityId;

    /**
     * 商品规格id
     */

    private Integer productId;

    /**
     * 拼团价格
     */

    private BigDecimal groupPrice;

    /**
     * 库存
     */
    private Short stock;


    /**
     * 销量
     */
    private Short saleNum;

    /**
     * 团长价格
     */
    private BigDecimal grouperPrice;


}
