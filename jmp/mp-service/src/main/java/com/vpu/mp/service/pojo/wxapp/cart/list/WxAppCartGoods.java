package com.vpu.mp.service.pojo.wxapp.cart.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.vpu.mp.service.pojo.wxapp.cart.CartConstant.GOODS_STATUS_ON_SALE;

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
    private Integer cartId;
    /**
     * 商品现在价格
     */
    private BigDecimal goodsPrice;
    /**
     * 最终价格
     */
    private BigDecimal prdPrice;
    /**
     * 添加购物车时价格
     */
    private BigDecimal originalPrice;
    /**
     * 添加购物车时原价格
     */
    private Integer cartNumber;
    /**
     * 是否选中
     */
    private Byte isChecked;
    /**
     *  活动类型
     */
    private Byte type;
    /**
     * 扩展字段: 如：换购挡位ID
     */
    private Integer extendId;

    private Integer storeId;
    private Integer userId;
    /**
     * 商品id
     */
    private Integer goodsId;
    private String goodsSn;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品
     */
    private String goodsImg;
    /**
     * 商品规格名
     */
    private String prdDesc;
    private Integer productId;
    private String prdSn;
    /**
     * 商品状态 1 在售 2 下架 3 删除 4 售罄 5
     */
    private Byte goodsStatus =GOODS_STATUS_ON_SALE;

    //***** 商品属性 *************
    /**
     * 商品
     */
    @JsonIgnore
    GoodsRecord goodsRecord;
    /**
     * 规格
     */
    @JsonIgnore
    GoodsSpecProductRecord productRecord;
    //***** 活动属性 **************
    /**
     * 活动id
     */
    private Integer activityId;
    /**
     * 活动类型
     */
    private Byte activityType;
    /**
     * 活动列表
     */
    private List<CartActivityInfo> cartActivityInfos = new ArrayList<>();

    public CartActivityInfo getActivity(Byte activityType) {
        return cartActivityInfos.stream().filter(cartActivityInfo -> cartActivityInfo.getStatus().equals(activityType)).findFirst().get();
    }


}
