package com.vpu.mp.service.pojo.wxapp.goods.search;

import com.vpu.mp.service.foundation.data.BaseConstant;
import com.vpu.mp.service.pojo.shop.base.BasePageParam;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * 小程序商品搜索界面-用户搜索条件
 * @author 李晓冰
 * @date 2019年12月09日
 */
@Getter
@Setter
public class GoodsSearchMpParam extends BasePageParam {

    /**用户id，controller 层获取*/
    private Integer userId;

    /**搜索输入的关键字*/
    private String keyWords;

    /**商品最低价*/
    private BigDecimal minPrice;

    /**商品最高价格*/
    private BigDecimal maxPrice;

    /**商家分类id集合，为了满足从商品分组处跳转时使用*/
    private List<Integer> sortIds;

    /**品牌id集合*/
    private List<Integer> brandIds;

    /**活动类型集合*/
    private List<Integer> activityTypes;

    /**标签id集合*/
    private List<Integer> labelIds;

    /**是否展示售罄商品,service层获取并设置
     * {@link com.vpu.mp.service.pojo.shop.goods.GoodsConstant#SOLD_OUT_GOODS_SHOW} 展示售罄
     */
    private Boolean soldOutGoodsShow;

    /**商品优惠券码*/
    private String couponSn;

    /**排序字段*/
    private SortItemEnum sortItem;
    /**排序方向*/
    private SortDirectionEnum sortDirection;

    /**当页面从商品分组跳转至搜索页面时此字段可能会被赋予指定值*/
    private List<Integer> goodsIds;

    /**从商品分组页面跳转至此*/
    public static final Byte PAGE_FROM_GROUP_LIST = 0;
    /**admin拼团活动分享码跳转 pageFrom =1*/
    public static final Byte PAGE_FROM_GROUP_BUY = BaseConstant.ACTIVITY_TYPE_GROUP_BUY;
    /**admin秒杀活动分享码跳转 pageFrom =5*/
    public static final Byte PAGE_FROM_SEC_KILL = BaseConstant.ACTIVITY_TYPE_SEC_KILL;
    /**从哪个页面跳转至搜索页面，目前用于区分从商品分组模块跳转至此，目前从分组跳转时未从es查数据*/
    private Byte pageFrom;
    /**用于多商品活动从admin端扫码进入搜索页展示该活动下的商品时使用 activityId*/
    private Integer actId;
}
