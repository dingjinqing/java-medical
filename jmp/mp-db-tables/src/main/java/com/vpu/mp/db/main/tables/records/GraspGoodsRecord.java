/*
 * This file is generated by jOOQ.
 */
package com.vpu.mp.db.main.tables.records;


import com.vpu.mp.db.main.tables.GraspGoods;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.annotation.Generated;

import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.9"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class GraspGoodsRecord extends TableRecordImpl<GraspGoodsRecord> {

    private static final long serialVersionUID = 1976089469;

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_id</code>.
     */
    public void setGoodsId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_id</code>.
     */
    public Integer getGoodsId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.shop_id</code>. 店铺ID
     */
    public void setShopId(Integer value) {
        set(1, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.shop_id</code>. 店铺ID
     */
    public Integer getShopId() {
        return (Integer) get(1);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.cat_id</code>.
     */
    public void setCatId(Integer value) {
        set(2, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.cat_id</code>.
     */
    public Integer getCatId() {
        return (Integer) get(2);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_sn</code>.
     */
    public void setGoodsSn(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_sn</code>.
     */
    public String getGoodsSn() {
        return (String) get(3);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_name</code>.
     */
    public void setGoodsName(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_name</code>.
     */
    public String getGoodsName() {
        return (String) get(4);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.brand_id</code>. 品牌ID
     */
    public void setBrandId(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.brand_id</code>. 品牌ID
     */
    public Integer getBrandId() {
        return (Integer) get(5);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_ad</code>. 广告词
     */
    public void setGoodsAd(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_ad</code>. 广告词
     */
    public String getGoodsAd() {
        return (String) get(6);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_number</code>. 库存
     */
    public void setGoodsNumber(Integer value) {
        set(7, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_number</code>. 库存
     */
    public Integer getGoodsNumber() {
        return (Integer) get(7);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_weight</code>.
     */
    public void setGoodsWeight(BigDecimal value) {
        set(8, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_weight</code>.
     */
    public BigDecimal getGoodsWeight() {
        return (BigDecimal) get(8);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.market_price</code>.
     */
    public void setMarketPrice(BigDecimal value) {
        set(9, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.market_price</code>.
     */
    public BigDecimal getMarketPrice() {
        return (BigDecimal) get(9);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.shop_price</code>.
     */
    public void setShopPrice(BigDecimal value) {
        set(10, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.shop_price</code>.
     */
    public BigDecimal getShopPrice() {
        return (BigDecimal) get(10);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.cost_price</code>. 成本价
     */
    public void setCostPrice(BigDecimal value) {
        set(11, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.cost_price</code>. 成本价
     */
    public BigDecimal getCostPrice() {
        return (BigDecimal) get(11);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_desc</code>.
     */
    public void setGoodsDesc(String value) {
        set(12, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_desc</code>.
     */
    public String getGoodsDesc() {
        return (String) get(12);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_img</code>.
     */
    public void setGoodsImg(String value) {
        set(13, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_img</code>.
     */
    public String getGoodsImg() {
        return (String) get(13);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.is_on_sale</code>. 是否在售，1在售，0下架
     */
    public void setIsOnSale(Byte value) {
        set(14, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.is_on_sale</code>. 是否在售，1在售，0下架
     */
    public Byte getIsOnSale() {
        return (Byte) get(14);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.is_delete</code>.
     */
    public void setIsDelete(Byte value) {
        set(15, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.is_delete</code>.
     */
    public Byte getIsDelete() {
        return (Byte) get(15);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_type</code>. 商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品
     */
    public void setGoodsType(Byte value) {
        set(16, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_type</code>. 商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品
     */
    public Byte getGoodsType() {
        return (Byte) get(16);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.deliver_template_id</code>. 运费模板ID
     */
    public void setDeliverTemplateId(Integer value) {
        set(17, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.deliver_template_id</code>. 运费模板ID
     */
    public Integer getDeliverTemplateId() {
        return (Integer) get(17);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_sale_num</code>. 销售数量
     */
    public void setGoodsSaleNum(Integer value) {
        set(18, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_sale_num</code>. 销售数量
     */
    public Integer getGoodsSaleNum() {
        return (Integer) get(18);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_collect_num</code>. 收藏数量
     */
    public void setGoodsCollectNum(Integer value) {
        set(19, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_collect_num</code>. 收藏数量
     */
    public Integer getGoodsCollectNum() {
        return (Integer) get(19);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.add_time</code>.
     */
    public void setAddTime(Timestamp value) {
        set(20, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.add_time</code>.
     */
    public Timestamp getAddTime() {
        return (Timestamp) get(20);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.update_time</code>. 最后修改时间
     */
    public void setUpdateTime(Timestamp value) {
        set(21, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.update_time</code>. 最后修改时间
     */
    public Timestamp getUpdateTime() {
        return (Timestamp) get(21);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.state</code>. 审核状态,0待审核 1 审核通过 2 违规下架
     */
    public void setState(Byte value) {
        set(22, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.state</code>. 审核状态,0待审核 1 审核通过 2 违规下架
     */
    public Byte getState() {
        return (Byte) get(22);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.reason</code>. 违规下架原因
     */
    public void setReason(String value) {
        set(23, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.reason</code>. 违规下架原因
     */
    public String getReason() {
        return (String) get(23);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.sub_account_id</code>. 子帐号ID，主要用于官方店铺
     */
    public void setSubAccountId(Integer value) {
        set(24, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.sub_account_id</code>. 子帐号ID，主要用于官方店铺
     */
    public Integer getSubAccountId() {
        return (Integer) get(24);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.sale_time</code>. 上架时间
     */
    public void setSaleTime(Timestamp value) {
        set(25, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.sale_time</code>. 上架时间
     */
    public Timestamp getSaleTime() {
        return (Timestamp) get(25);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.limit_buy_num</code>. 最少起购数量，0不限购
     */
    public void setLimitBuyNum(Integer value) {
        set(26, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.limit_buy_num</code>. 最少起购数量，0不限购
     */
    public Integer getLimitBuyNum() {
        return (Integer) get(26);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.unit</code>. 商品单位
     */
    public void setUnit(String value) {
        set(27, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.unit</code>. 商品单位
     */
    public String getUnit() {
        return (String) get(27);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.add_sale_num</code>. 虚假销量
     */
    public void setAddSaleNum(Integer value) {
        set(28, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.add_sale_num</code>. 虚假销量
     */
    public Integer getAddSaleNum() {
        return (Integer) get(28);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.limit_max_num</code>. 最多起购数量，0不限购
     */
    public void setLimitMaxNum(Integer value) {
        set(29, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.limit_max_num</code>. 最多起购数量，0不限购
     */
    public Integer getLimitMaxNum() {
        return (Integer) get(29);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.sale_type</code>. 上架状态,0立即上架， 1审核通过 2 加入仓库
     */
    public void setSaleType(Byte value) {
        set(30, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.sale_type</code>. 上架状态,0立即上架， 1审核通过 2 加入仓库
     */
    public Byte getSaleType() {
        return (Byte) get(30);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.sort_id</code>.
     */
    public void setSortId(Integer value) {
        set(31, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.sort_id</code>.
     */
    public Integer getSortId() {
        return (Integer) get(31);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_video</code>. 视频
     */
    public void setGoodsVideo(String value) {
        set(32, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_video</code>. 视频
     */
    public String getGoodsVideo() {
        return (String) get(32);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_video_img</code>. 视频首图
     */
    public void setGoodsVideoImg(String value) {
        set(33, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_video_img</code>. 视频首图
     */
    public String getGoodsVideoImg() {
        return (String) get(33);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_video_size</code>. 视频尺寸
     */
    public void setGoodsVideoSize(Integer value) {
        set(34, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_video_size</code>. 视频尺寸
     */
    public Integer getGoodsVideoSize() {
        return (Integer) get(34);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_video_id</code>. 视频ID
     */
    public void setGoodsVideoId(Integer value) {
        set(35, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_video_id</code>. 视频ID
     */
    public Integer getGoodsVideoId() {
        return (Integer) get(35);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_page_id</code>. 详情页装修模板ID
     */
    public void setGoodsPageId(Integer value) {
        set(36, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_page_id</code>. 详情页装修模板ID
     */
    public Integer getGoodsPageId() {
        return (Integer) get(36);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.is_page_up</code>. 是否在文本区域上方
     */
    public void setIsPageUp(Byte value) {
        set(37, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.is_page_up</code>. 是否在文本区域上方
     */
    public Byte getIsPageUp() {
        return (Byte) get(37);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.is_card_exclusive</code>. 是否会员卡专属
     */
    public void setIsCardExclusive(Byte value) {
        set(38, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.is_card_exclusive</code>. 是否会员卡专属
     */
    public Byte getIsCardExclusive() {
        return (Byte) get(38);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.base_sale</code>. 初始销量
     */
    public void setBaseSale(Integer value) {
        set(39, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.base_sale</code>. 初始销量
     */
    public Integer getBaseSale() {
        return (Integer) get(39);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.on_sale_shop_id</code>. 发布店铺
     */
    public void setOnSaleShopId(String value) {
        set(40, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.on_sale_shop_id</code>. 发布店铺
     */
    public String getOnSaleShopId() {
        return (String) get(40);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.source</code>. 商品来源,0：店铺自带；1、2..等：不同类型店铺第三方抓取自带商品来源
     */
    public void setSource(Byte value) {
        set(41, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.source</code>. 商品来源,0：店铺自带；1、2..等：不同类型店铺第三方抓取自带商品来源
     */
    public Byte getSource() {
        return (Byte) get(41);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.is_control_price</code>. 是否控价,0：不控价；1：控价
     */
    public void setIsControlPrice(Byte value) {
        set(42, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.is_control_price</code>. 是否控价,0：不控价；1：控价
     */
    public Byte getIsControlPrice() {
        return (Byte) get(42);
    }

    /**
     * Setter for <code>mini_main.b2c_grasp_goods.goods_flag</code>. 商品来源平台1：欧派；2：寺库
     */
    public void setGoodsFlag(Byte value) {
        set(43, value);
    }

    /**
     * Getter for <code>mini_main.b2c_grasp_goods.goods_flag</code>. 商品来源平台1：欧派；2：寺库
     */
    public Byte getGoodsFlag() {
        return (Byte) get(43);
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached GraspGoodsRecord
     */
    public GraspGoodsRecord() {
        super(GraspGoods.GRASP_GOODS);
    }

    /**
     * Create a detached, initialised GraspGoodsRecord
     */
    public GraspGoodsRecord(Integer goodsId, Integer shopId, Integer catId, String goodsSn, String goodsName, Integer brandId, String goodsAd, Integer goodsNumber, BigDecimal goodsWeight, BigDecimal marketPrice, BigDecimal shopPrice, BigDecimal costPrice, String goodsDesc, String goodsImg, Byte isOnSale, Byte isDelete, Byte goodsType, Integer deliverTemplateId, Integer goodsSaleNum, Integer goodsCollectNum, Timestamp addTime, Timestamp updateTime, Byte state, String reason, Integer subAccountId, Timestamp saleTime, Integer limitBuyNum, String unit, Integer addSaleNum, Integer limitMaxNum, Byte saleType, Integer sortId, String goodsVideo, String goodsVideoImg, Integer goodsVideoSize, Integer goodsVideoId, Integer goodsPageId, Byte isPageUp, Byte isCardExclusive, Integer baseSale, String onSaleShopId, Byte source, Byte isControlPrice, Byte goodsFlag) {
        super(GraspGoods.GRASP_GOODS);

        set(0, goodsId);
        set(1, shopId);
        set(2, catId);
        set(3, goodsSn);
        set(4, goodsName);
        set(5, brandId);
        set(6, goodsAd);
        set(7, goodsNumber);
        set(8, goodsWeight);
        set(9, marketPrice);
        set(10, shopPrice);
        set(11, costPrice);
        set(12, goodsDesc);
        set(13, goodsImg);
        set(14, isOnSale);
        set(15, isDelete);
        set(16, goodsType);
        set(17, deliverTemplateId);
        set(18, goodsSaleNum);
        set(19, goodsCollectNum);
        set(20, addTime);
        set(21, updateTime);
        set(22, state);
        set(23, reason);
        set(24, subAccountId);
        set(25, saleTime);
        set(26, limitBuyNum);
        set(27, unit);
        set(28, addSaleNum);
        set(29, limitMaxNum);
        set(30, saleType);
        set(31, sortId);
        set(32, goodsVideo);
        set(33, goodsVideoImg);
        set(34, goodsVideoSize);
        set(35, goodsVideoId);
        set(36, goodsPageId);
        set(37, isPageUp);
        set(38, isCardExclusive);
        set(39, baseSale);
        set(40, onSaleShopId);
        set(41, source);
        set(42, isControlPrice);
        set(43, goodsFlag);
    }
}
