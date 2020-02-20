package com.vpu.mp.service.pojo.wxapp.market.packagesale;

import com.vpu.mp.service.foundation.util.PageResult;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author: 王兵兵
 * @create: 2020-02-20 10:40
 **/
@Getter
@Setter
public class PackageSaleGoodsListVo {
    /** 状态，0正常可用，1活动不存在，2活动未开始，3活动已过期 ，4活动设置了专属会员卡可参与，但该会员没有对应的卡，  */
    private Byte state;

    /**
     * 当前分组下可选的商品列表
     */
    private PageResult<Goods> goods;

    /**
     * tab切换栏数据
     */
    private List<GoodsGroup>  tabList;

    /**
     * 顶部活动解释文本
     */
    private Title title;

    @Setter
    @Getter
    public static class Goods{
        //商品原数据
        private Integer goodsId;
        private String goodsName;
        /**
         * 在售状态1在售,0下架
         */
        private Byte isOnSale;
        /**
         * 商品库存，该字段是通过商品规格计算而来
         */
        private Integer goodsNumber;

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
        private Integer sortId;
        private Integer catId;


        //活动数据
        /**
         * 取价结果类型，0商品原价，1等级会员价，2限时降价，3首单特惠价
         */
        private Byte goodsPriceAction;
        private BigDecimal goodsPrice;

        private BigDecimal maxPrice;
    }

    @Setter
    @Getter
    public static class GoodsGroup{
        /** 商品组名称 */
        private String groupName;
        /** 至少需要选择件数 */
        private Integer goodsNumber;
        /** 用户已经选择件数 */
        private Integer selectNumber;
    }

    @Getter
    @Setter
    public static class Title{
        private Byte packageType;

        /**
         * 满件数折扣类型
         */
        private Integer discountTotalGoodsNumber;
        private BigDecimal discountTotalRatio;

        /**
         * 几件一口价类型
         */
        private BigDecimal totalMoney;
        private Integer totalGoodsNumber;

    }
}
