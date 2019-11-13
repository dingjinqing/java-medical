package com.vpu.mp.service.pojo.shop.overview.commodity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

/**
 * author liufei
 * date 2019/7/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductOverviewVo {
    /**
     * 在架商品数
     */
    private int onShelfGoodsNum;
    /**
     * 动销商品数(统计时间内，销量不为0的商品数量)
     */
    private int soldGoodsNum;
    /**
     * 被访问商品数
     */
    private int visitedGoodsNum;
    /**
     * uv(商品访客数)
     */
    private int goodsUserVisit;
    /**
     * pv(商品浏览量)
     */
    private int goodsPageviews;
    /**
     * 加购人数
     */
    private int purchaseNum;
    /**
     * 加购件数
     */
    private int purchaseQuantity;
    /**
     * 付款商品数
     */
    private int paidGoodsNum;
    /**
     * 下单商品数
     */
    private int orderGoodsNum;
    /**
     * 商品访问付款转化率
     */
    private BigDecimal visit2paid = BigDecimal.ZERO;

    /**
     * The Change rate.较前N日的变化率
     * K: field-name
     * V: 变化率
     */
    Map<String, BigDecimal> changeRate;
}