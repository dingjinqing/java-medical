package com.vpu.mp.service.pojo.wxapp.cart.list;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author 孔德成
 * @date 2019/10/16 11;49
 */
@Data
public class WxAppCartGoods {
    // 购物车;列表id
    private Integer recId;
    // 商品id
    private Integer goodsId;
    // 商品名称
    private String goodsName;
// 规格id
    private Integer productId;
    // 是否选中
    private Byte isChecked;
    // 商品规格
    private String goodsSpecs;
    // 商品价格
    private BigDecimal goodsPrice;
    // 购物车商品价格
    private BigDecimal cartGoodsPrice;
    /**商品活动类型  1：加价购主商品， 2： 满折满减*/
    private Byte action;
    // 关联ID 加价购ID， 满折满减ID
    private Integer identityId;
    /**扩展字段: 如：换购挡位ID*/
    private Integer extendId;
    /**
     * 商品数量
     */
    private Integer goodsNumber;
    /**
     * 商品数量
     */
    private String goodsImg;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     *  规格数量
     */
    private Integer prdNumber;
    /**
     * 图片
     */
    private String prdImg;
    /**
     * 删除标识
     */
    private Byte delFlag;
    private Integer limitBuyNum;
    private Integer limitMaxNum;
    private Integer goodsType;
    private Byte isDelete;
    private Byte isOnSale;
    private String tip;
}
