package com.vpu.mp.service.shop.goods.es;

import com.vpu.mp.config.es.annotation.EsFiled;
import com.vpu.mp.config.es.annotation.EsFiledTypeConstant;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * ES 商品
 * @author 卢光耀
 * @date 2019-09-26 10:40
 *
*/

@Data
public class EsGoods  {
    @EsFiled(name = "goods_id",type = EsFiledTypeConstant.INTEGER)
    private Integer goodsId;
    @EsFiled(name = "shop_id",type = EsFiledTypeConstant.INTEGER)
    private Integer shopId;
    @EsFiled(name = "goods_name",type = EsFiledTypeConstant.TEXT,analyzer = "ik_max_word")
    private String goodsName;
    /**
     * 商品广告词
     */
    @EsFiled(name = "goods_ad",type = EsFiledTypeConstant.TEXT)
    private String goodsAd;
    /**
     * 商品货号
     */
    @EsFiled(name = "goods_sn",type = EsFiledTypeConstant.TEXT)
    private String goodsSn;
    /**
     * 平台分类
     */
    @EsFiled(name = "cat_id",type = EsFiledTypeConstant.INTEGER)
    private Integer catId;
    /**
     * 商品主图
     */
    @EsFiled(name = "goods_img",type = EsFiledTypeConstant.KEYWORD)
    private String goodsImg;

    /**
     * 商品单位
     */
    @EsFiled(name = "unit",type = EsFiledTypeConstant.KEYWORD)
    private String unit;
    /**
     * 商品商家分类
     */
    @EsFiled(name = "sort_id",type = EsFiledTypeConstant.INTEGER)
    private Integer sortId;

    /**
     * 商品品牌
     */
    @EsFiled(name = "brand_id",type = EsFiledTypeConstant.INTEGER)
    private Integer brandId;
    /**
     * 市场价格
     */
    @EsFiled(name = "market_price",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal marketPrice;
    /**
     * 最小限购数量
     */
    @EsFiled(name = "limit_buy_num",type = EsFiledTypeConstant.INTEGER)
    private Integer limitBuyNum;

    /**
     * 初始销量
     */
    @EsFiled(name = "add_sale_num",type = EsFiledTypeConstant.INTEGER)
    private Integer addSaleNum;

    /**
     * 商品重量
     */
    @EsFiled(name = "goods_weight",type = EsFiledTypeConstant.SCALED_FLOAT,scaledNumber = "1000")
    private BigDecimal goodsWeight;

    /**
     * 是否会员专享
     */
    @EsFiled(name = "is_card_exclusive",type = EsFiledTypeConstant.BYTE)
    private Byte isCardExclusive;

    /**
     * 在售状态1在售,0下架
     */
    @EsFiled(name = "is_on_sale",type = EsFiledTypeConstant.BYTE)
    private Byte isOnSale;
    /**
     * 商品上架状态 0立即上架， 1审核通过 2 加入仓库
     */
    @EsFiled(name = "sale_type",type = EsFiledTypeConstant.BYTE)
    private Byte saleType;

    /**
     * 是否在自定义内容上方
     */
    @EsFiled(name = "is_page_up",type = EsFiledTypeConstant.BYTE)
    private Byte isPageUp;
    /**
     * 详情页装修模板id
     */
    @EsFiled(name = "goods_page_id",type = EsFiledTypeConstant.INTEGER)
    private Integer goodsPageId;


    /**
     * 商品库存，该字段是通过商品规格计算而来
     */
    @EsFiled(name = "goods_number",type = EsFiledTypeConstant.INTEGER)
    private Integer goodsNumber;
    /**
     * 商品价格，商品规格中的最低价格，（对于默认规格和自定义规格计算方式是一样的）
     */
    @EsFiled(name = "shop_price",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal shopPrice;
    /**
     * 商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品
     */
    @EsFiled(name = "goods_type",type = EsFiledTypeConstant.BYTE)
    private Byte goodsType;
    /**
     *  销售数量
     */
    @EsFiled(name = "goods_sale_num",type = EsFiledTypeConstant.INTEGER)
    private Integer goodsSaleNum;
    /**
     *  收藏数量
     */
    @EsFiled(name = "goods_collect_num",type = EsFiledTypeConstant.INTEGER)
    private Integer goodsCollectNum;

    /**
     *  子帐号id，主要用于官方店铺
     */
    @EsFiled(name = "sub_account_id",type = EsFiledTypeConstant.INTEGER)
    private Integer subAccountId;
    /**
     *  审核状态,0待审核 1 审核通过 2 违规下架
     */
    @EsFiled(name = "state",type = EsFiledTypeConstant.BYTE)
    private Integer state;

    /**
     *  成本价
     */
    @EsFiled(name = "cost_price",type = EsFiledTypeConstant.SCALED_FLOAT)
    private Integer costPrice;
    /**
     *  商品来源,0：店铺自带；1、2..等：不同类型店铺第三方抓取自带商品来源
     */
    @EsFiled(name = "source",type = EsFiledTypeConstant.BYTE)
    private Integer source;
    /**
     * 是否控价：0不控价，1控价（不可修改价格）
     */
    @EsFiled(name = "is_control_price",type = EsFiledTypeConstant.BYTE)
    private Integer isControlPrice;
    /**
     * 商品浏览量
     */
    @EsFiled(name = "pv",type = EsFiledTypeConstant.INTEGER)
    private Integer pv;
    /**
     * 商品评论数
     */
    @EsFiled(name = "comment_num",type = EsFiledTypeConstant.INTEGER)
    private Integer comment_num;
    /**
     * 商品初始销量
     */
    @EsFiled(name = "base_sale",type = EsFiledTypeConstant.INTEGER)
    private Integer base_sale;

    /**
     * 商品v1会员等级价格
     */
    @EsFiled(name = "v1",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal v1;
    /**
     * 商品v2会员等级价格
     */
    @EsFiled(name = "v2",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal v2;
    /**
     * 商品v3会员等级价格
     */
    @EsFiled(name = "v3",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal v3;
    /**
     * 商品v4会员等级价格
     */
    @EsFiled(name = "v4",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal v4;
    /**
     * 商品v5会员等级价格
     */
    @EsFiled(name = "v5",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal v5;
    /**
     * 商品v6会员等级价格
     */
    @EsFiled(name = "v6",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal v6;
    /**
     * 商品v7会员等级价格
     */
    @EsFiled(name = "v7",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal v7;
    /**
     * 商品v8会员等级价格
     */
    @EsFiled(name = "v8",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal v8;
    /**
     * 商品v9会员等级价格
     */
    @EsFiled(name = "v9",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal v9;
    /**
     * 商品展示价格
     */
    @EsFiled(name = "show_price",type = EsFiledTypeConstant.SCALED_FLOAT)
    private BigDecimal showPrice;

    /**
     * 商家编码（拼接）
     */
    @EsFiled(name = "prd_sns",type = EsFiledTypeConstant.TEXT)
    private String prdSns;
    /**
     * 平台分类名称（空格拼接）
     */
    @EsFiled(name = "cat_name",type = EsFiledTypeConstant.TEXT)
    private String catName;

    @EsFiled(name = "first_cat_id",type = EsFiledTypeConstant.INTEGER)
    private Integer firstCatId;
    @EsFiled(name = "second_cat_id",type = EsFiledTypeConstant.INTEGER)
    private Integer secondCatId;
    @EsFiled(name = "third_cat_id",type = EsFiledTypeConstant.INTEGER)
    private Integer thirdCatId;

    /**
     * 商家分类名称（空格拼接）
     */
    @EsFiled(name = "sort_name",type = EsFiledTypeConstant.TEXT)
    private String sortName;

    @EsFiled(name = "first_sort_id",type = EsFiledTypeConstant.INTEGER)
    private Integer firstSortId;
    @EsFiled(name = "second_sort_id",type = EsFiledTypeConstant.INTEGER)
    private Integer secondSortId;
    /**
     * 品牌名称
     */
    @EsFiled(name = "brand_name",type = EsFiledTypeConstant.TEXT)
    private String brandName;
    /**
     * 品牌名称
     */
    @EsFiled(name = "goods_label",type = EsFiledTypeConstant.INTEGER)
    private List<Integer> goodsLabel;
}
