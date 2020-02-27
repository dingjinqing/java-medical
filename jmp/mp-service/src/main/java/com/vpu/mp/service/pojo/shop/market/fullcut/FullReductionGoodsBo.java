package com.vpu.mp.service.pojo.shop.market.fullcut;

import com.vpu.mp.service.pojo.wxapp.cart.list.CartActivityInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * 满折满减活动商品信息
 * @author 孔德成
 * @date 2020/2/27 11:14
 */
@Getter
@Setter
public class FullReductionGoodsBo {

    private Integer fullReductionId;
    private Integer fullReductionRuleId;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 商品规格id
     */
    private Integer productId;
    /**
     * 购物车id
     */
    private Integer cartId;
    /**
     * 数量
     */
    private Integer num;
    /**
     * 钱
     */
    private BigDecimal money;

    private CartActivityInfo.FullReductionRule fullReductionRule;
}
