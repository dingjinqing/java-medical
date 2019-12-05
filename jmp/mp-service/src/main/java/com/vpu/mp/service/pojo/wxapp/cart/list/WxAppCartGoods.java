package com.vpu.mp.service.pojo.wxapp.cart.list;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 孔德成
 * @date 2019/10/16 11;49
 */
@Data
public class WxAppCartGoods {

    //***** 购物车 *************
    /**
     * 购物车;id
     */
    private Long recId;
    /**
     * 购物车数量
     */
    private Integer goodsNumber;
    /**
     * 购物车商品价格(添加购物车是价格)
     */
    private BigDecimal goodsPrice;
    /**
     * 商品规格价格(最后价格)
     */
    private BigDecimal prdPrice;
    /**
     * 是否选中
     */
    private Byte isChecked;
    //***** 商品属性 *************
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 规格id
     */
    private Integer prdId;
    /**
     * 商家分类
     */
    private Integer sortId;
    /**
     * 平台分类
     */
    private Integer cartId;
    /**
     * 品牌分类
     */
    private Integer brandId;
    /**
     * 是否是会员专属
     */
    private Byte isCardExclusive;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品规格
     */
    private String goodsSpecs;
            /**
             * 商品规格名
             */
    private String prdDesc;
    /**
     * 商品规格数量
     */
    private Integer prdNumber;
    /**
     * 商品最大购买数量
     */
    private Integer limitBuyNum;
    /**
     * 商品最小购买数量
     */
    private Integer limitMaxNum;
    /**
     * 图片
     */
    private String prdImg;
    /**
     * 商品图片
     */
    private String goodsImg;
    /**
     * 商品状态 1 在售 2 下架 3 删除 4 售罄 5
     */
    private Byte goodsStatus;
    /**
     * 是否在售 默认 1
     */
    private Byte isOnSale;
    /**
     * 商品删除标识 默认 0
     */
    private Byte delFlag;
    //***** 活动属性 **************
    /**
     * 活动列表
     */
    private List<CartActivityInfo> cartActivityInfos;
    /**
     * 商品类型，0普通商品，1拼团商品，2分销，3砍价商品 4积分商品 5秒杀商品
     */
    private Byte goodsType;
    /**
     * 活动价格后
     */
    private BigDecimal activityPrice;
    /**
     * 活动类型  1 会员等级价格
     */
    private Byte activityType;
    /**
     * 商品活动类型  1：加价购主商品， 2： 满折满减
     */
    private Byte action;
    /**
     * 1会员卡 2限时降价 3,4 首单特惠
     */
    private Byte goodsPriceAction;
    /**
     * 关联ID 加价购ID， 满折满减ID
     */
    private Integer identityId;
    /**
     * 扩展字段: 如：换购挡位ID
     */
    private Integer extendId;

    /**
     * 转化为info 页面展示格式
     * @return   CartGoodsInfo
     */
    public CartGoodsInfo toInfo() {
        CartGoodsInfo cartGoodsInfo = new CartGoodsInfo();
        cartGoodsInfo.setRecId(recId);
        cartGoodsInfo.setGoodsId(getGoodsId());
        cartGoodsInfo.setPrdId(prdId);
        cartGoodsInfo.setGoodsStatus(goodsStatus);
        cartGoodsInfo.setCartNumber(goodsNumber);
        cartGoodsInfo.setIsChecked(isChecked);
        cartGoodsInfo.setCartPrice(getPrdPrice());
        cartGoodsInfo.setPrdPrice(prdPrice);
        cartGoodsInfo.setPrdNumber(prdNumber);
        cartGoodsInfo.setPrdDesc(prdDesc);
        cartGoodsInfo.setLimitBuyNum(limitBuyNum);
        cartGoodsInfo.setLimitMaxNum(limitMaxNum);
        cartGoodsInfo.setPrdImg(prdImg);
        cartGoodsInfo.setGoodsImg(goodsImg);
        cartGoodsInfo.setGoodsName(goodsName);
        cartGoodsInfo.setGoodsSpecs(goodsSpecs);
        cartGoodsInfo.setActivityInfos(cartActivityInfos);
        return cartGoodsInfo;
    }
}
