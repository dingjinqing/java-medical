package com.vpu.mp.service.pojo.wxapp.cart.list;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vpu.mp.db.shop.tables.records.GoodsRecord;
import com.vpu.mp.db.shop.tables.records.GoodsSpecProductRecord;
import com.vpu.mp.service.foundation.data.BaseConstant;
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
     * 商品现在价格80
     */
    private BigDecimal goodsPrice;
    /**
     * 最终价格 参加活动后的价格80
     */
    private BigDecimal prdPrice;
    /**
     * 添加购物车时价格100
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
     *  指定的活动类型，21满折满减
     */
    private Byte type;
    /**
     * 扩展字段: 如：换购挡位ID
     */
    private Integer extendId;

    /**
     * 最终价格的取价来源活动，0普通商品，2分销改价，6限时降价，18首单特惠，23会员专享
     */
    private Byte priceAction = BaseConstant.ACTIVITY_TYPE_GENERAL;
    /**
     * 活动限购数量，例如最终价格是限时降价活动价时，该活动的限购数量
     */
    private Integer limitAmount;
    /**
     * 超限购买设置标记，1禁止超限购买，0超限全部恢复原价
     * 部分活动会设置
     */
    private Byte limitFlag =1;

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
     * 商品规格数据
     */
    private String prdImg;
    private String prdDesc;
    private Integer productId;
    private String prdSn;
    /**
     * 商品最少限购数量
     */
    private Integer limitBuyNum;
    /**
     * 商品最大限购数量
     */
    private Integer limitMaxNum;

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
        if (cartActivityInfos==null||cartActivityInfos.size()==0){
            return null;
        }
        return cartActivityInfos.stream().filter(cartActivityInfo -> cartActivityInfo.getActivityType().equals(activityType)).findFirst().orElse(null);
    }


}
