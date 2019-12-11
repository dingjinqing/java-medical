package com.vpu.mp.service.pojo.wxapp.market.groupbuy;

import com.vpu.mp.service.pojo.shop.goods.spec.GoodsSpec;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 拼团商品信息
 * @author 孔德成
 * @date 2019/12/10 18:06
 */
@Getter
@Setter
public class GroupBuyGoodsInfo {

    private Integer goodsId;
    private String goodsImg;
    private String goodsName;
    private Integer goodsNumber;
    /**
     * 拼团价格区间
     */
    private BigDecimal maxGroupBuyPrice;
    /**
     * 拼团最低价
     */
    private BigDecimal minGroupBuyPrice;
    /**
     * 拼团库存
     */
    private Integer groupBuygoodsNum;




}
