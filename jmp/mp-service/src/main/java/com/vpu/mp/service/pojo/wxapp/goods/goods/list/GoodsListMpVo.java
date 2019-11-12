package com.vpu.mp.service.pojo.wxapp.goods.goods.list;

import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsActivityBaseMp;
import com.vpu.mp.service.pojo.wxapp.goods.goods.GoodsBaseMpVo;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李晓冰
 * @date 2019年10月12日
 * 小程序和装修商品模块返回实体类
 */
@Setter
@Getter
public class GoodsListMpVo extends GoodsBaseMpVo {
    private String goodsImg;
    private Integer commentNum;
    //商品所拥有的活动处理信息
    private List<GoodsActivityBaseMp> goodsActivity = new ArrayList<>();
    private GoodsLabelMpVo label;

    private BigDecimal shopPrice;
    private BigDecimal linePrice;
    private BigDecimal realPrice;

    /** 指定该商品的详情页营销活动id */
    private Integer activityId;
    /**指定该商品的详情页营销活动类型*/
    private Byte activityType;
}
