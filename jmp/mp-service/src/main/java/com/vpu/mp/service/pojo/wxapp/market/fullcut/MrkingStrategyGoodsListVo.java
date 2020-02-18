package com.vpu.mp.service.pojo.wxapp.market.fullcut;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 王兵兵
 * @create: 2020-02-18 10:12
 **/
@Getter
@Setter
public class MrkingStrategyGoodsListVo {
    /** 状态，0正常可用，1活动不存在，2活动未开始，3活动已过期 ，4活动设置了专属会员卡可参与，但该会员没有对应的卡，  */
    private Byte state;

    private List<Goods> goods;


    @Setter
    @Getter
    public static class Goods{
        private Integer goodsId;
        private String goodsName;
        /**
         * 商品货号
         */
        private String goodsSn;
        /**
         * 在售状态1在售,0下架
         */
        private Byte isOnSale;
        /**
         * 商品库存，该字段是通过商品规格计算而来
         */
        private Integer goodsNumber;
        /**
         * 0 未删除； 1： 删除
         */
        private Byte delFlag;

        /**
         * 商品主图
         */
        private String goodsImg;
        /**
         * 商品价格，商品规格中的最低价格，（对于默认规格和自定义规格计算方式是一样的）
         */
        private BigDecimal shopPrice;
        /**
         * 市场价格
         */
        private BigDecimal marketPrice;
        private Byte goodsType;
        /**
         * 是否会员专享
         */
        private Byte isCardExclusive;
    }
}
