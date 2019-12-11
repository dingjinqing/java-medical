package com.vpu.mp.service.pojo.wxapp.goods.search;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 小程序商品搜索界面-用户搜索条件
 * @author 李晓冰
 * @date 2019年12月09日
 */
@Data
public class GoodsSearchMpParam {

    /**用户id，controller 层获取*/
    private Integer userId;

    /**搜索输入的关键字*/
    private String keyWords;

    /**商品最低价*/
    private BigDecimal minPrice;

    /**商品最高价格*/
    private BigDecimal maxPrice;

    /**商家分类id*/
    private Integer sortId;

    /**品牌id集合*/
    private List<Integer> brandIds;

    /**活动类型集合*/
    private List<Integer> activityTypes;

    /**标签id集合*/
    private List<Integer> labelIds;

    /**
     * 是否展示售罄商品,从service层获取
     * {@link com.vpu.mp.service.pojo.shop.goods.GoodsConstant#SOLD_OUT_GOODS_SHOW 展示售罄
     */
    private Byte soldOutGoods;

    /**商品优惠券码*/
    private String couponSn;

    /**排序字段*/
    private SortItemEnum sortItem;
    /**排序方向*/
    private SortDirectionEnum sortDirection;
}
