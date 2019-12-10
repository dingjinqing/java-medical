package com.vpu.mp.service.pojo.wxapp.cart.list;

import com.vpu.mp.service.pojo.wxapp.cart.activity.GoodsActivityInfo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车商品信息
 * @author 孔德成
 * @date 2019/11/5 16:47
 */
@Setter
@Getter
public class CartGoodsInfo {
    /**
     * 购物车;id
     */
    private Long recId;
    /**
     * 商品id
     */
    private Integer goodsId;
    /**
     * 规格id
     */
    private Integer prdId;
    /**
     * 商品状态 1 在售 2 下架 3 删除 4 售罄 5专享
     */
    private Byte goodsStatus =1;
    /**
     * 购物车数量
     */
    private Integer cartNumber;
    /**
     * 是否选中
     */
    private Byte isChecked;
    /**
     * 购物车商品价格(规格原价)
     */
    private BigDecimal cartPrice;
    /**
     * 商品规格价格
     */
    private BigDecimal prdPrice;
    /**
     * 商品规格数量
     */
    private Integer prdNumber;
    /**
     * 商品规格名
     */
    private String prdDesc;
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
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品规格
     */
    private String goodsSpecs;
    /**
     * 活动类型
     */
    private List<GoodsActivityInfo> activityInfos;

}
